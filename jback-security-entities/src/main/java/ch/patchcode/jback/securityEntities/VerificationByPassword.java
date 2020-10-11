package ch.patchcode.jback.securityEntities;

import org.inferred.freebuilder.FreeBuilder;

import java.util.UUID;

@FreeBuilder
public abstract class VerificationByPassword implements VerificationMean {

    @Override
    public abstract UUID getId();

    public abstract String getUsername();

    public abstract String getPassword();

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

    public static class Builder extends VerificationByPassword_Builder {
    }
}
