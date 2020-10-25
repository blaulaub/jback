package ch.patchcode.jback.securityEntities.verificationMeans;

import org.inferred.freebuilder.FreeBuilder;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public final class VerificationByEmail implements VerificationMean, Serializable {

    private final UUID id;

    private final String emailAddress;

    public VerificationByEmail(UUID id, String emailAddress) {
        this.id = requireNonNull(id);
        this.emailAddress = requireNonNull(emailAddress);
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public <R> R accept(Visitor<R> registrationHandler) {

        return registrationHandler.visit(this);
    }

    @Override
    public Draft toNewDraft() {
        return new Draft.Builder()
                .setEmailAddress(this.getEmailAddress())
                .build();
    }

    @FreeBuilder
    public abstract static class Draft implements VerificationMean.Draft {

        public abstract String getEmailAddress();

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }

        public static class Builder extends VerificationByEmail_Draft_Builder {
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationByEmail that = (VerificationByEmail) o;
        return id.equals(that.id) &&
                emailAddress.equals(that.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, emailAddress);
    }
}
