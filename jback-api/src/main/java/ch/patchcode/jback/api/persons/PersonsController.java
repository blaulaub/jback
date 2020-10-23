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
     * Let the currently authenticated principal create a person for himself.
     * <p>
     * As part of this, a new principal is created for the new person, and the new
     * principal takes over the current session.
     * <p>
     * Ideally, this is part of the registration, where anybody, after passing the
     * initial verification, then provides some more details about her own personality.
     * In that process, the current, temporary principal will be replaced by the
     * permanent principal for that person.
     */
    @PostMapping("me")
    public Person createOwnPerson(
            @RequestBody @ApiParam Person.MeDraft draft
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

    /**
     * Lets an authorized user create a new person.
     *
     * @param draft contains information about the person
     * @return the new person
     */
    @PostMapping
    public Person createClientPerson(
            @RequestBody @ApiParam Person.Draft draft
    ) {

        var context = SecurityContextHolder.getContext();
        var callerAuth = (Principal) context.getAuthentication();
        var person = personService.create(draft.toDomain());
        authorizationManager.addClient(callerAuth, person);
        return fromDomain(person);
    }
}
