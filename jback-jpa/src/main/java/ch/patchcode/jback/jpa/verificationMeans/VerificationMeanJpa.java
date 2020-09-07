package ch.patchcode.jback.jpa.verificationMeans;

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

    public abstract <R> R accept(VerificationMeanJpa.Visitor<R> registrationHandler);

    public interface Visitor<R> {

        R visit(ConsoleVerification consoleVerification);

        R visit(EmailVerification emailVerification);

        R visit(SmsVerification smsVerification);
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
