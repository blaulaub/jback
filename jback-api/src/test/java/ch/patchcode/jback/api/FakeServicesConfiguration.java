package ch.patchcode.jback.api;

import ch.patchcode.jback.core.common.Address;
import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.core.persons.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class FakeServicesConfiguration {

    @Bean
    public PersonService getPersonService() {

        return new PersonService() {
            @Override
            public Person getPerson(UUID id) {
                return new Person.Builder()
                        .setId(id)
                        .setFirstName("Tom")
                        .setLastName("Sawyer")
                        .setAddress(
                                new Address.Builder()
                                        .setLines(new String[] {
                                                "Technoparkstrasse 1",
                                                "8051 Zürich"
                                        })
                                        .build()
                        )
                        .build();
            }
        };
    }
}
