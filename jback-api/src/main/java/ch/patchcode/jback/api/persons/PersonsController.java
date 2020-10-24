package ch.patchcode.jback.api.persons;

import ch.patchcode.jback.api.exceptions.NotFoundException;
import ch.patchcode.jback.core.persons.PersonService;
import ch.patchcode.jback.presentation.AuthorizationManager;
import ch.patchcode.jback.securityEntities.authentications.Principal;
import ch.patchcode.jback.securityEntities.verificationMeans.VerificationMean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static ch.patchcode.jback.api.persons.Person.fromDomain;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/v1/persons")
@Api(tags = "Persons")
public class PersonsController {

    private final PersonService personService;
    private final AuthorizationManager authorizationManager;

    @Autowired
    public PersonsController(
            PersonService personService,
            @Qualifier("presentation.authorizationManager") AuthorizationManager authorizationManager
    ) {

        this.personService = personService;
        this.authorizationManager = authorizationManager;
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
    ) {

        var context = SecurityContextHolder.getContext();
        var callerAuth = (Principal) context.getAuthentication();
        var person = personService.create(draft.toDomain());
        authorizationManager.addClient(callerAuth, person);
        return fromDomain(person);
    }

    /**
     * Creates a {@link Person} with authentication means.
     * <p>
     * The resulting {@link Person} is able to login to the system.
     */
    @PostMapping("with-password")
    public Person createPersonWithPassword(
            @RequestBody @ApiParam PersonWithPasswordDraft draft
    ) {

        var context = SecurityContextHolder.getContext();
        var callerAuth = (Principal) context.getAuthentication();

        var person = personService.create(draft.toDomain());

        List<VerificationMean.Draft> means = callerAuth.getMeans().stream()
                .map(VerificationMean::toNewDraft)
                .collect(toList());
        means.add(draft.toVerificationMean());

        var auth = authorizationManager.createAuthorizationFor(person, means);

        context.setAuthentication(auth);

        return fromDomain(person);
    }
}
