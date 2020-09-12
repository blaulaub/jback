package ch.patchcode.jback.jpa.principals;

import ch.patchcode.jback.jpa.persons.PersonJpa;
import ch.patchcode.jback.jpa.registration.ToRegistrationConverter;
import ch.patchcode.jback.jpa.verificationMeans.VerificationMeanJpa;
import ch.patchcode.jback.jpa.verificationMeans.VerificationMeanJpaRepository;
import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.security.authentications.PersonalAuthentication;
import ch.patchcode.jback.security.authentications.PersonalAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
public class PrincipalJpaRepoWrapper implements PersonalAuthenticationRepository {

    private final PrincipalJpaRepository principalJpaRepository;
    private final VerificationMeanJpaRepository verificationMeanJpaRepository;

    @Autowired
    public PrincipalJpaRepoWrapper(
            PrincipalJpaRepository principalJpaRepository,
            VerificationMeanJpaRepository verificationMeanJpaRepository
    ) {

        this.principalJpaRepository = principalJpaRepository;
        this.verificationMeanJpaRepository = verificationMeanJpaRepository;
    }

    @Override
    public PersonalAuthentication create(PersonalAuthentication personalAuthentication) {

        PrincipalJpa principal = persist(personalAuthentication);

        List<VerificationMean> means = personalAuthentication.getMeans().stream()
                .map(it -> it.accept(new VerificationMean.VerificationMeanVisitor<VerificationMeanJpa>() {
                    @Override
                    public VerificationMeanJpa visit(VerificationMean.VerificationByConsole verificationByConsole) {
                        VerificationMeanJpa.ConsoleVerification consoleVerification = new VerificationMeanJpa.ConsoleVerification();
                        consoleVerification.setPrincipal(principal);
                        return consoleVerification;
                    }

                    @Override
                    public VerificationMeanJpa visit(VerificationMean.VerificationByEmail verificationByEmail) {
                        VerificationMeanJpa.EmailVerification emailVerification = new VerificationMeanJpa.EmailVerification();
                        emailVerification.setPrincipal(principal);
                        emailVerification.setEmail(verificationByEmail.getEmailAddress());
                        return emailVerification;
                    }

                    @Override
                    public VerificationMeanJpa visit(VerificationMean.VerificationBySms verificationBySms) {
                        VerificationMeanJpa.SmsVerification smsVerification = new VerificationMeanJpa.SmsVerification();
                        smsVerification.setPrincipal(principal);
                        smsVerification.setPhoneNumber(verificationBySms.getPhoneNumber());
                        return smsVerification;
                    }
                }))
                .map(verificationMeanJpaRepository::save)
                .map(it -> it.accept(new VerificationMeanJpa.Visitor<VerificationMean>() {
                    @Override
                    public VerificationMean visit(VerificationMeanJpa.ConsoleVerification consoleVerification) {
                        return new VerificationMean.VerificationByConsole();
                    }

                    @Override
                    public VerificationMean visit(VerificationMeanJpa.EmailVerification emailVerification) {
                        return new VerificationMean.VerificationByEmail.Builder()
                                .setEmailAddress(emailVerification.getEmail())
                                .build();
                    }

                    @Override
                    public VerificationMean visit(VerificationMeanJpa.SmsVerification smsVerification) {
                        return new VerificationMean.VerificationBySms.Builder()
                                .setPhoneNumber(smsVerification.getPhoneNumber())
                                .build();
                    }
                })).collect(toList());

        return new PersonalAuthentication(
                principal.getSelf().toDomain(),
                means
        );
    }

    private PrincipalJpa persist(PersonalAuthentication personalAuthentication) {

        var draft = new PrincipalJpa();
        draft.setSelf(PersonJpa.fromDomain(personalAuthentication.getHolder()));
        draft.setAuthorities(emptyList());
        draft.setClients(emptyList());
        var principal = principalJpaRepository.save(draft);
        return principal;
    }
}
