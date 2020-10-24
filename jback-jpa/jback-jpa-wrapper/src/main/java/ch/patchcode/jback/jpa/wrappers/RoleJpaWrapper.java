package ch.patchcode.jback.jpa.wrappers;

import ch.patchcode.jback.coreEntities.Club;
import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.coreEntities.roles.AdminRole;
import ch.patchcode.jback.coreEntities.roles.MemberRole;
import ch.patchcode.jback.coreEntities.roles.Role;
import ch.patchcode.jback.coreEntities.roles.RoleRepository;
import ch.patchcode.jback.jpa.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class RoleJpaWrapper implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;
    private final PersonJpaRepository personJpaRepository;
    private final ClubJpaRepository clubJpaRepository;

    @Autowired
    public RoleJpaWrapper(RoleJpaRepository roleJpaRepository, PersonJpaRepository personJpaRepository, ClubJpaRepository clubJpaRepository) {
        this.roleJpaRepository = roleJpaRepository;
        this.personJpaRepository = personJpaRepository;
        this.clubJpaRepository = clubJpaRepository;
    }

    @Override
    public Optional<Role> findById(UUID id) {

        return roleJpaRepository.findById(id).map(RoleJpa::toDomain);
    }

    @Override
    public Role create(Role.Draft draft) {

        RoleJpa role = draft.accept(new Role.Draft.Visitor<>() {

            @Override
            public RoleJpa visit(MemberRole.Draft draft) {

                var role = new RoleJpa.MemberRoleJpa();
                role.setPerson(personJpaRepository.toJpaIfConsistent(draft.getPerson()));
                role.setClub(clubJpaRepository.toJpaIfConsistent(draft.getOrganisation()));
                return role;
            }

            @Override
            public RoleJpa visit(AdminRole.Draft draft) {

                var role = new RoleJpa.AdminRoleJpa();
                role.setPerson(personJpaRepository.toJpaIfConsistent(draft.getPerson()));
                role.setClub(clubJpaRepository.toJpaIfConsistent(draft.getOrganisation()));
                return role;
            }
        });

        return roleJpaRepository.save(role).toDomain();
    }

    @Override
    public List<Role> findByPersonAndClub(Person person, Club club) {

        return roleJpaRepository.findByPersonAndClub(
                personJpaRepository.toJpaIfConsistent(person),
                clubJpaRepository.toJpaIfConsistent(club)
        ).stream()
                .map(RoleJpa::toDomain)
                .collect(toList());
    }

    @Override
    public List<Role> findByPersonIn(List<Person> persons) {

        List<PersonJpa> personJpas = persons.stream()
                .map(personJpaRepository::toJpaIfConsistent)
                .collect(toList());

        List<RoleJpa> roleJpaRepositoryByPersonIn = roleJpaRepository.findByPersonIn(personJpas);

        return roleJpaRepositoryByPersonIn.stream()
                .map(RoleJpa::toDomain)
                .collect(toList());
    }
}
