package ch.patchcode.jback.core;

import ch.patchcode.jback.core.clubs.ClubRepository;
import ch.patchcode.jback.core.clubs.ClubService;
import ch.patchcode.jback.core.clubs.impl.ClubServiceImpl;
import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.core.persons.PersonRepository;
import ch.patchcode.jback.core.persons.PersonService;
import ch.patchcode.jback.core.persons.impl.PersonServiceImpl;
import ch.patchcode.jback.secBase.AuthorizationManager;
import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.secBase.secModelImpl.Principal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Spring configuration that automatically contains all services (and other
 * components) of this package and below through component scan. It does not
 * pre-configure any dependencies (repositories), these need to be configured
 * elsewhere.
 */
@Configuration
public class CoreConfiguration {

    @Bean
    public <
            TVerificationMean extends VerificationMean,
            TPrincipal extends Principal<TVerificationMean>>
    PersonService<TVerificationMean, TPrincipal> getPersonService(
            AuthorizationManager<Person<TVerificationMean>, TVerificationMean, TPrincipal> authorizationManager,
            PersonRepository<TVerificationMean> personRepository) {

        return new PersonServiceImpl<>(authorizationManager, personRepository);
    }

    @Bean
    public <
            TVerificationMean extends VerificationMean>
    ClubService<TVerificationMean> getClubService(ClubRepository<TVerificationMean> clubRepository) {

        return new ClubServiceImpl<>(clubRepository);
    }
}
