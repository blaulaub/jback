package ch.patchcode.jback.securityEntities.verificationMeans;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public final class VerificationBySms implements VerificationMean, Serializable {

    private final UUID id;

    private final String phoneNumber;

    public VerificationBySms(UUID id, String phoneNumber) {
        this.id = requireNonNull(id);
        this.phoneNumber = requireNonNull(phoneNumber);
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public <R> R accept(Visitor<R> registrationHandler) {

        return registrationHandler.visit(this);
    }

    @Override
    public Draft toNewDraft() {
        return new Draft(this.getPhoneNumber());
    }

    public final static class Draft implements VerificationMean.Draft, Serializable {

        private final String phoneNumber;

        public Draft(String phoneNumber) {
            this.phoneNumber = requireNonNull(phoneNumber);
        }

        public String getPhoneNumber() {
            return phoneNumber;
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
            return phoneNumber.equals(draft.phoneNumber);
        }

        @Override
        public int hashCode() {
            return Objects.hash(phoneNumber);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationBySms that = (VerificationBySms) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber);
    }
}
