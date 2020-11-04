package ch.patchcode.jback.core;

import ch.patchcode.jback.core.clubs.ClubMemberService;
import ch.patchcode.jback.core.clubs.ClubMembershipApplicationService;
import ch.patchcode.jback.core.clubs.ClubService;
import ch.patchcode.jback.core.clubs.impl.ClubMemberServiceImpl;
import ch.patchcode.jback.core.clubs.impl.ClubMembershipApplicationServiceImpl;
import ch.patchcode.jback.core.clubs.impl.ClubServiceImpl;
import ch.patchcode.jback.core.impl.RoleServiceImpl;
import ch.patchcode.jback.core.persons.PersonService;
import ch.patchcode.jback.core.persons.impl.PersonServiceImpl;
import ch.patchcode.jback.coreEntities.roles.RoleRepository;
import ch.patchcode.jback.coreRepositories.ClubMembershipApplicationRepository;
import ch.patchcode.jback.coreRepositories.ClubRepository;
import ch.patchcode.jback.coreRepositories.PersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfiguration {

    @Bean
    public PersonService getPersonService(PersonRepository personRepository) {

        return new PersonServiceImpl(personRepository);
    }

    @Bean
    public ClubService getClubService(ClubRepository clubRepository) {

        return new ClubServiceImpl(clubRepository);
    }

    @Bean
    public ClubMemberService getClubMemberService(
            ClubRepository clubRepository,
            RoleRepository roleRepository
    ) {

        return new ClubMemberServiceImpl(
                clubRepository,
                roleRepository
        );
    }

    @Bean
    public ClubMembershipApplicationService getClubMembershipApplicationService(
            ClubMembershipApplicationRepository clubMembershipApplicationRepository
    ) {

        return new ClubMembershipApplicationServiceImpl(clubMembershipApplicationRepository);
    }

    @Bean
    public RoleService getRoleService(RoleRepository roleRepository) {

        return new RoleServiceImpl(roleRepository);
    }
}
