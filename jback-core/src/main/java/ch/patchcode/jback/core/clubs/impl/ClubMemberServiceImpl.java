package ch.patchcode.jback.core.clubs.impl;

import ch.patchcode.jback.coreEntities.NotAllowedException;
import ch.patchcode.jback.core.clubs.ClubMemberService;
import ch.patchcode.jback.core.clubs.ClubNotFoundException;
import ch.patchcode.jback.coreEntities.Club;
import ch.patchcode.jback.coreEntities.ClubRepository;
import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.coreEntities.roles.AdminRole;
import ch.patchcode.jback.coreEntities.roles.MemberRole;
import ch.patchcode.jback.coreEntities.roles.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

public class ClubMemberServiceImpl implements ClubMemberService {

    private static final Logger LOG = LoggerFactory.getLogger(ClubMemberServiceImpl.class);

    private final ClubRepository clubRepository;
    private final RoleRepository roleRepository;

    public ClubMemberServiceImpl(ClubRepository clubRepository, RoleRepository roleRepository) {
        this.clubRepository = clubRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void addMember(UUID clubId, Person person) throws ClubNotFoundException {

        Club club = resolveClub(clubId);

        var previousRoles = roleRepository.findByPersonAndClub(person, club);

        if (previousRoles.stream().anyMatch(it -> it instanceof MemberRole)) {

            LOG.info("person {} is already a member of {}, add-member request will be ignored", person.getId(), club);
            return;
        }

        var draft = new MemberRole.Draft.Builder()
                .setPerson(person)
                .setOrganisation(club)
                .build();

        roleRepository.create(draft);
    }

    @Override
    public void addAdmin(UUID clubId, Person person) throws ClubNotFoundException, NotAllowedException {

        Club club = resolveClub(clubId);

        var previousRoles = roleRepository.findByPersonAndClub(person, club);

        if (previousRoles.stream().noneMatch(it -> it instanceof MemberRole)) {

            LOG.info("person {} is no member of {}, add-admin request rejected", person.getId(), club);
            throw new NotAllowedException();
        }

        if (previousRoles.stream().anyMatch(it -> it instanceof AdminRole)) {

            LOG.info("person {} is already an admin of {}, add-admin request will be ignored", person.getId(), club);
            return;
        }

        var draft = new AdminRole.Draft.Builder()
                .setPerson(person)
                .setOrganisation(club)
                .build();

        roleRepository.create(draft);
    }

    private Club resolveClub(UUID clubId) throws ClubNotFoundException {
        Optional<Club> optionalClub = clubRepository.findById(clubId);

        if (optionalClub.isEmpty()) {

            throw new ClubNotFoundException();
        }

        return optionalClub.get();
    }
}
