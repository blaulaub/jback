package ch.patchcode.jback.api;

import ch.patchcode.jback.core.common.Address;
import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.core.persons.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.UUID;

@Configuration
@EnableWebMvc
@Import(ApiConfiguration.class)
public class ApiTestConfiguration {

    @Bean
    public WebMvcConfigurer getWebMvcConfigurer() {

        return new WebMvcConfigurer() {
        };
    }

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
