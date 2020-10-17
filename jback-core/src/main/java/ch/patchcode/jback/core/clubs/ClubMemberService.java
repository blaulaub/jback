package ch.patchcode.jback.core.clubs;

import ch.patchcode.jback.coreEntities.Person;

import java.util.UUID;

public interface ClubMemberService {

    void addPerson(UUID clubId, Person person);
}
