package ch.patchcode.jback.core.clubs;

import ch.patchcode.jback.coreEntities.NotAllowedException;
import ch.patchcode.jback.coreEntities.Person;

import java.util.UUID;

public interface ClubMemberService {

    void addMember(UUID clubId, Person person) throws ClubNotFoundException;

    void addAdmin(UUID clubId, Person toDomain) throws ClubNotFoundException, NotAllowedException;
}
