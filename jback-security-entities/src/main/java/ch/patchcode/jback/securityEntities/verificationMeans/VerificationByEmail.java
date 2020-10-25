package ch.patchcode.jback.securityEntities.verificationMeans;

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
        return new Draft(this.getEmailAddress());
    }

    public final static class Draft implements VerificationMean.Draft, Serializable {

        private final String emailAddress;

        public Draft(String emailAddress) {
            this.emailAddress = requireNonNull(emailAddress);
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Draft draft = (Draft) o;
            return emailAddress.equals(draft.emailAddress);
        }

        @Override
        public int hashCode() {
            return Objects.hash(emailAddress);
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
