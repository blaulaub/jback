package ch.patchcode.jback.securityEntities.verificationMeans;

import org.inferred.freebuilder.FreeBuilder;

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
        return new Draft.Builder()
                .setUsername(this.getUsername())
                .setPassword(this.getPassword())
                .build();
    }

    @FreeBuilder
    public abstract static class Draft implements VerificationMean.Draft {

        public abstract String getUsername();

        public abstract String getPassword();

        public static Draft of(String username, String password) {

            return new Builder()
                    .setUsername(username)
                    .setPassword(password)
                    .build();
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }

        public static class Builder extends VerificationByPassword_Draft_Builder {
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
