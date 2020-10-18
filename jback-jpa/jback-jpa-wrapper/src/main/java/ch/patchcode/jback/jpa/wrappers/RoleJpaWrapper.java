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
                role.setPerson(toJpaIfConsistent(draft.getPerson()));
                role.setClub(RoleJpaWrapper.this.toJpaIfConsistent(draft.getOrganisation()));
                return role;
            }

            @Override
            public RoleJpa visit(AdminRole.Draft draft) {

                var role = new RoleJpa.AdminRoleJpa();
                role.setPerson(toJpaIfConsistent(draft.getPerson()));
                role.setClub(RoleJpaWrapper.this.toJpaIfConsistent(draft.getOrganisation()));
                return role;
            }
        });

        return roleJpaRepository.save(role).toDomain();
    }

    @Override
    public List<Role> findByPersonIn(List<Person> persons) {

        List<PersonJpa> personJpas = persons.stream()
                .map(this::toJpaIfConsistent)
                .collect(toList());

        List<RoleJpa> roleJpaRepositoryByPersonIn = roleJpaRepository.findByPersonIn(personJpas);

        return roleJpaRepositoryByPersonIn.stream()
                .map(RoleJpa::toDomain)
                .collect(toList());
    }

    private PersonJpa toJpaIfConsistent(Person person) {

        var personJpa = personJpaRepository.findById(person.getId());
        if (personJpa.isEmpty()) {
            throw new RuntimeException("person not found");
        }
        if (personJpa.map(PersonJpa::toDomain)
                .filter(it -> it.equals(person))
                .isEmpty()) {
            throw new RuntimeException("person mismatch (outdated?)");
        }
        return personJpa.get();
    }

    private ClubJpa toJpaIfConsistent(Club club) {

        var clubJpa = clubJpaRepository.findById(club.getId());
        if (clubJpa.isEmpty()) {
            throw new RuntimeException("club not found");
        }
        if (clubJpa.map(ClubJpa::toDomain)
                .filter(it -> it.equals(club))
                .isEmpty()) {
            throw new RuntimeException("club mismatch (outdated?)");
        }
        return clubJpa.get();
    }
}
