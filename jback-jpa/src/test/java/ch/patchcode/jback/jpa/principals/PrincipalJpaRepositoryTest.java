package ch.patchcode.jback.jpa.principals;

import ch.patchcode.jback.jpa.JpaTestConfiguration;
import ch.patchcode.jback.jpa.persons.PersonJpa;
import ch.patchcode.jback.jpa.persons.PersonJpaRepository;
import ch.patchcode.jback.jpa.verificationMeans.VerificationMeanJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JpaTestConfiguration.class})
class PrincipalJpaRepositoryTest {

    private final PrincipalJpaRepository principalRepository;

    // not under test, but we also need this for related entities
    private final PersonJpaRepository personJpaRepository;

    @Autowired
    public PrincipalJpaRepositoryTest(
            PrincipalJpaRepository principalRepository,
            PersonJpaRepository personJpaRepository
    ) {
        this.principalRepository = principalRepository;
        this.personJpaRepository = personJpaRepository;
    }

    @Test
    void save_and_findById() {

        // arrange
        List<PersonJpa> persons = personJpaRepository.saveAll(singletonList(personOf("Huckleberry", "Finn")));
        List<String> authorities = singletonList("CAN_HELP_JIM");
        List<VerificationMeanJpa> verificationMeans = singletonList(smsVerificationOf("+491806672255"));

        // act
        var id = principalRepository.save(principalOf(persons, authorities, verificationMeans)).getId();
        var principal = principalRepository.findById(id);

        // assert
        assertTrue(principal.isPresent());
    }

    private PrincipalJpa principalOf(List<PersonJpa> persons, List<String> authorities, List<VerificationMeanJpa> verificationMeans) {
        var principal = new PrincipalJpa();
        principal.setPersons(persons);
        principal.setAuthorities(authorities);
        principal.setVerificationMeans(verificationMeans);
        return principal;
    }

    private PersonJpa personOf(String firstName, String lastName) {
        var person = new PersonJpa();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }

    private VerificationMeanJpa.SmsVerification smsVerificationOf(String phoneNumber) {
        var smsVerification = new VerificationMeanJpa.SmsVerification();
        smsVerification.setPhoneNumber(phoneNumber);
        return smsVerification;
    }
}