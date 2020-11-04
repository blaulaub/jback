package ch.patchcode.jback.api.persons;

import ch.patchcode.jback.api.exceptions.ForbiddenException;
import ch.patchcode.jback.api.exceptions.NotFoundException;
import ch.patchcode.jback.core.persons.PersonService;
import ch.patchcode.jback.coreRepositories.NotAllowedException;
import ch.patchcode.jback.presentation.AuthenticationManager;
import ch.patchcode.jback.presentation.impl.SpringAuthentication;
import ch.patchcode.jback.securityEntities.authentications.PersonalAuthentication;
import ch.patchcode.jback.securityEntities.authentications.Principal;
import ch.patchcode.jback.securityEntities.authentications.SuperuserAuthentication;
import ch.patchcode.jback.securityEntities.authentications.TemporaryAuthentication;
import ch.patchcode.jback.securityEntities.verificationMeans.VerificationByPassword;
import ch.patchcode.jback.securityEntities.verificationMeans.VerificationMean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ch.patchcode.jback.api.persons.Person.fromDomain;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/v1/persons")
@Api(tags = "Persons")
public class PersonsController {

    private final PersonService personService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public PersonsController(
            PersonService personService,
            AuthenticationManager authenticationManager
    ) {

        this.personService = personService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("{id}")
    public Person getPersonById(@PathVariable("id") UUID id) throws NotFoundException {

        return personService.getPerson(id).map(Person::fromDomain).orElseThrow(NotFoundException::new);
    }

    /**
     * Creates a {@link Person} without authentication means.
     * <p>
     * The resulting {@link Person} will not be able to log in.
     */
    @PostMapping
    public Person createPerson(
            @RequestBody @ApiParam Person.Draft draft
    ) throws ForbiddenException {

        var currentPrincipal = principalFromRequest();
        var newPerson = personService.create(draft.toDomain());

        try {
            SpringAuthentication<?> auth = null;
            auth = authenticationManager.addPersonToPrincipal(currentPrincipal, newPerson);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (NotAllowedException e) {
            throw new ForbiddenException(e);
        }

        return fromDomain(newPerson);
    }

    /**
     * Creates a {@link Person} with authentication means.
     * <p>
     * The resulting {@link Person} is able to login to the system. To that purpose,
     * a username-password-verification is created from the corresponding data given
     * in the {@link PersonWithPasswordDraft}. Furthermore, the person obtains copies
     * of the verification means of the current principal.
     */
    @PostMapping("with-password")
    public Person createPersonWithPassword(
            @RequestBody @ApiParam PersonWithPasswordDraft draft
    ) {

        var currentPrincipal = principalFromRequest();

        var person = personService.create(draft.toDomain());

        var means = new ArrayList<VerificationMean.Draft>();
        means.addAll(verificationMeansFromPrincipal(currentPrincipal));
        means.add(verificationMeanFromDraft(draft));

        var auth = authenticationManager.createAuthorizationFor(person, means);
        SecurityContextHolder.getContext().setAuthentication(auth);

        return fromDomain(person);
    }

    @GetMapping("for-current-principal")
    public List<Person> getPersonsForCurrentPrincipal() {

        return principalFromRequest().accept(new Principal.ResultVisitor<>() {

            @Override
            public List<Person> visit(PersonalAuthentication personalAuthentication) {

                return personalAuthentication.getPersons().stream()
                        .map(Person::fromDomain)
                        .collect(toList());
            }

            @Override
            public List<Person> visit(TemporaryAuthentication temporaryAuthentication) {

                // of course a temporary has none
                return emptyList();
            }

            @Override
            public List<Person> visit(SuperuserAuthentication superuserAuthentication) {

                // technical the superuser owns all persons, but returning all would be suicidal
                return emptyList();
            }
        });
    }

    private List<VerificationMean.Draft> verificationMeansFromPrincipal(Principal currentPrincipal) {

        return currentPrincipal.getMeans().stream()
                .map(VerificationMean::toNewDraft)
                .collect(toList());
    }

    private VerificationByPassword.Draft verificationMeanFromDraft(PersonWithPasswordDraft draft) {

        return draft.toVerificationMean();
    }

    private Principal principalFromRequest() {

        var context = SecurityContextHolder.getContext();
        return (Principal) context.getAuthentication();
    }
}
