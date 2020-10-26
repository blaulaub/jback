package ch.patchcode.jback.coreEntities;

import org.inferred.freebuilder.FreeBuilder;

import java.util.UUID;

@FreeBuilder
public interface ClubMembershipApplication {

    UUID getId();
    Person getPerson();
    Club getClub();

    @FreeBuilder
    interface Draft {

        Person getPerson();
        Club getClub();

        class Builder extends ClubMembershipApplication_Draft_Builder {}
    }

    class Builder extends ClubMembershipApplication_Builder {}
}
