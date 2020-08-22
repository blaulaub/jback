package ch.patchcode.jback.api;

import ch.patchcode.jback.api.persons.PersonsController;
import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.core.persons.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.UUID;

@Configuration
@EnableWebMvc
public class SpringTestConfiguration {

    @Bean
    public PersonsController getPersonsController(PersonService personService) {

        return new PersonsController(personService);
    }

    @Bean
    public PersonService getPersonService() {

        return new PersonService() {

            @Override
            public Person getPerson(UUID id) {
                return null;
            }
        };
    }
}
