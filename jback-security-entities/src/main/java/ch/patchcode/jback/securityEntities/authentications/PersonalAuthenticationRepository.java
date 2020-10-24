package ch.patchcode.jback.securityEntities.authentications;

import ch.patchcode.jback.coreEntities.Person;

import java.util.Optional;

public interface PersonalAuthenticationRepository {

    PersonalAuthentication create(PersonalAuthentication.Draft draft);

    // TODO does not belong here
    Optional<PersonalAuthentication> findByUserIdentification(String userIdentification);

    PersonalAuthentication addPerson(PersonalAuthentication personalAuthentication, Person person);
}
