package ch.patchcode.jback.jpa.entities;

import ch.patchcode.jback.securityEntities.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity(name = RegistrationJpa.ENTITY_NAME)
@Table(name = RegistrationJpa.ENTITY_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class RegistrationJpa {

    public static final String ENTITY_NAME = "Registrations";

    private static final FromDomainConverter fromDomainConverter = new FromDomainConverter();

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

    public abstract PendingRegistration toDomain();

    public static RegistrationJpa fromDomain(PendingRegistration.Draft draft) {

        return fromDomainConverter.convert(draft);
    }

    /**
     * Helper method from the base class, providing a preconfigured {@link PendingRegistration.Builder}
     * aiding child classes to implement {@link #toDomain()}
     */
    final protected PendingRegistration.Builder toDomainBaseBuilder() {

        var builder = new PendingRegistration.Builder();
        builder.setId(PendingRegistration.Id.of(getId()));
        builder.setFirstName(getFirstName());
        builder.setLastName(getLastName());
        builder.setVerificationCode(getVerificationCode());
        builder.setExpiresAt(Instant.ofEpochMilli(getExpiresAt()));
        return builder;
    }

    @Entity
    @DiscriminatorValue("console")
    public static class ConsoleRegistrationJpa extends RegistrationJpa {

        @Override
        public PendingRegistration toDomain() {

            return toDomainBaseBuilder()
                    .setVerificationMean(new VerificationByConsole())
                    .build();
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
        public PendingRegistration toDomain() {

            return toDomainBaseBuilder()
                    .setVerificationMean(new VerificationByEmail.Builder()
                            .setEmailAddress(getEmail())
                            .build())
                    .build();
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
        public PendingRegistration toDomain() {

            return toDomainBaseBuilder()
                    .setVerificationMean(new VerificationBySms.Builder()
                            .setPhoneNumber(getPhoneNumber())
                            .build())
                    .build();
        }
    }

    private static class FromDomainConverter implements VerificationMean.Visitor<RegistrationJpa> {

        public RegistrationJpa convert(PendingRegistration.Draft pendingRegistration) {

            var result = pendingRegistration.getVerificationMean().accept(this);
            result.setFirstName(pendingRegistration.getFirstName());
            result.setLastName(pendingRegistration.getLastName());
            result.setVerificationCode(pendingRegistration.getVerificationCode());
            result.setExpiresAt(pendingRegistration.getExpiresAt().toEpochMilli());
            return result;
        }

        @Override
        public RegistrationJpa visit(VerificationByConsole verificationByConsole) {

            return new ConsoleRegistrationJpa();
        }

        @Override
        public RegistrationJpa visit(VerificationByEmail verificationByEmail) {

            var result = new EmailRegistrationJpa();
            result.setEmail(verificationByEmail.getEmailAddress());
            return result;
        }

        @Override
        public RegistrationJpa visit(VerificationBySms verificationBySms) {

            var result = new SmsRegistrationJpa();
            result.setPhoneNumber(verificationBySms.getPhoneNumber());
            return result;
        }

        @Override
        public RegistrationJpa visit(VerificationByUsernameAndPassword verificationByUsernameAndPassword) {

            // if there was username and password, then registration is already over
            throw new IllegalArgumentException();
        }
    }
}
