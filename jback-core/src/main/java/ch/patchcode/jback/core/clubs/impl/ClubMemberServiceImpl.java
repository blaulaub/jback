package ch.patchcode.jback.core.clubs.impl;

import ch.patchcode.jback.core.clubs.ClubMemberService;
import ch.patchcode.jback.coreEntities.ClubRepository;
import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.coreEntities.roles.MemberRole;
import ch.patchcode.jback.coreEntities.roles.RoleRepository;

import java.util.UUID;

public class ClubMemberServiceImpl implements ClubMemberService {

    private final ClubRepository clubRepository;
    private final RoleRepository roleRepository;

    public ClubMemberServiceImpl(ClubRepository clubRepository, RoleRepository roleRepository) {
        this.clubRepository = clubRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void addPerson(UUID clubId, Person person) {

        var club = clubRepository.findById(clubId).get();

        var draft = new MemberRole.Draft.Builder()
                .setPerson(person)
                .setOrganisation(club)
                .build();

        roleRepository.create(draft);
    }
}
