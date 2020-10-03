package ch.patchcode.jback.core;

import ch.patchcode.jback.core.clubs.ClubService;
import ch.patchcode.jback.core.clubs.impl.ClubServiceImpl;
import ch.patchcode.jback.coreEntities.ClubRepository;
import ch.patchcode.jback.coreEntities.PersonRepository;
import ch.patchcode.jback.core.persons.PersonService;
import ch.patchcode.jback.core.persons.impl.PersonServiceImpl;
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
}
