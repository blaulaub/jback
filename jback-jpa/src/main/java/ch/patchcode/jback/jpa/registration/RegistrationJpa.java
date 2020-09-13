package ch.patchcode.jback.jpa.registration;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = RegistrationJpa.ENTITY_NAME)
@Table(name = RegistrationJpa.ENTITY_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class RegistrationJpa {

    public static final String ENTITY_NAME = "Registrations";

    public abstract <R> R accept(Visitor<R> registrationHandler);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    public UUID getId() {
        return id;
    }

    private String firstName;

    private String lastName;

    private String verificationCode;

    private Long expiresAt;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Long expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Entity
    @DiscriminatorValue("console")
    public static class ConsoleRegistrationJpa extends RegistrationJpa {

        @Override
        public <R> R accept(Visitor<R> registrationHandler) {

            return registrationHandler.visit(this);
        }
    }

    @Entity
    @DiscriminatorValue("email")
    public static class EmailRegistrationJpa extends RegistrationJpa {

        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public <R> R accept(Visitor<R> registrationHandler) {

            return registrationHandler.visit(this);
        }
    }

    @Entity
    @DiscriminatorValue("sms")
    public static class SmsRegistrationJpa extends RegistrationJpa {

        private String phoneNumber;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        @Override
        public <R> R accept(Visitor<R> registrationHandler) {

            return registrationHandler.visit(this);
        }
    }

    interface Visitor<R> {

        R visit(ConsoleRegistrationJpa registrationByConsole);

        R visit(EmailRegistrationJpa registrationByEmail);

        R visit(SmsRegistrationJpa registrationBySms);
    }
}
