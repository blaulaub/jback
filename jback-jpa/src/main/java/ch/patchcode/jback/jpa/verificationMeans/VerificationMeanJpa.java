package ch.patchcode.jback.jpa.verificationMeans;

import ch.patchcode.jback.jpa.personalAuthentications.PersonalAuthenticationJpa;
import ch.patchcode.jback.secBase.VerificationMean;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = VerificationMeanJpa.ENTITY_NAME)
@Table(name = VerificationMeanJpa.ENTITY_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class VerificationMeanJpa {

    public static final String ENTITY_NAME = "VerificationMeans";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    public UUID getId() {
        return id;
    }

    @ManyToOne
    private PersonalAuthenticationJpa principal;

    public PersonalAuthenticationJpa getPrincipal() {
        return principal;
    }

    public void setPrincipal(PersonalAuthenticationJpa principal) {
        this.principal = principal;
    }

    public abstract <R> R accept(VerificationMeanJpa.Visitor<R> registrationHandler);

    public interface Visitor<R> {

        R visit(ConsoleVerification consoleVerification);

        R visit(EmailVerification emailVerification);

        R visit(SmsVerification smsVerification);
    }

    public static VerificationMeanJpa fromDomain(PersonalAuthenticationJpa principal, VerificationMean mean) {

        return mean.accept(new VerificationMean.VerificationMeanVisitor<>() {
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
        });
    }

    public VerificationMean toDomain() {

        return this.accept(new VerificationMeanJpa.Visitor<>() {

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
        });
    }

    @Entity
    @DiscriminatorValue("console")
    public static class ConsoleVerification extends VerificationMeanJpa {

        @Override
        public <R> R accept(Visitor<R> verificationVisitor) {

            return verificationVisitor.visit(this);
        }
    }

    @Entity
    @DiscriminatorValue("email")
    public static class EmailVerification extends VerificationMeanJpa {

        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public <R> R accept(Visitor<R> verificationVisitor) {

            return verificationVisitor.visit(this);
        }
    }

    @Entity
    @DiscriminatorValue("sms")
    public static class SmsVerification extends VerificationMeanJpa {

        private String phoneNumber;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        @Override
        public <R> R accept(Visitor<R> verificationVisitor) {

            return verificationVisitor.visit(this);
        }
    }
}
