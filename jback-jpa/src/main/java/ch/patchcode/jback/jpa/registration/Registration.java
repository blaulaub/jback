package ch.patchcode.jback.jpa.registration;

import ch.patchcode.jback.core.registration.VerificationMean;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
public abstract class Registration {

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
}
