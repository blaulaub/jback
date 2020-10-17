package ch.patchcode.jback.core;

import ch.patchcode.jback.core.clubs.ClubMemberService;
import ch.patchcode.jback.core.clubs.ClubService;
import ch.patchcode.jback.core.clubs.impl.ClubMemberServiceImpl;
import ch.patchcode.jback.core.clubs.impl.ClubServiceImpl;
import ch.patchcode.jback.core.persons.PersonService;
import ch.patchcode.jback.core.persons.impl.PersonServiceImpl;
import ch.patchcode.jback.coreEntities.ClubRepository;
import ch.patchcode.jback.coreEntities.PersonRepository;
import ch.patchcode.jback.coreEntities.roles.RoleRepository;
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
}
