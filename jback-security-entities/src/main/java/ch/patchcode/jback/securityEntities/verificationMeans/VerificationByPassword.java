package ch.patchcode.jback.securityEntities.verificationMeans;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public final class VerificationByPassword implements VerificationMean, Serializable {

    private final UUID id;

    private final String username;

    private final String password;

    public VerificationByPassword(UUID id, String username, String password) {

        this.id = requireNonNull(id);
        this.username = requireNonNull(username);
        this.password = requireNonNull(password);
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public <R> R accept(Visitor<R> registrationHandler) {

        return registrationHandler.visit(this);
    }

    @Override
    public Draft toNewDraft() {
        return new Draft(this.getUsername(), this.getPassword());
    }

    public final static class Draft implements VerificationMean.Draft, Serializable {

        private final String username;

        private final String password;

        public Draft(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
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
            return username.equals(draft.username) &&
                    password.equals(draft.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(username, password);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationByPassword that = (VerificationByPassword) o;
        return id.equals(that.id) &&
                username.equals(that.username) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}
