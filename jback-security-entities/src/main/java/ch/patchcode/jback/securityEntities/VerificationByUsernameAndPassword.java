package ch.patchcode.jback.securityEntities;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public abstract class VerificationByUsernameAndPassword implements VerificationMean {

    public abstract String getUsername();

    public abstract String getPassword();

    @Override
    public <R> R accept(Visitor<R> registrationHandler) {

        return registrationHandler.visit(this);
    }

    @FreeBuilder
    public abstract static class Draft implements VerificationMean.Draft {

        public abstract String getUsername();

        public abstract String getPassword();

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }

        public static class Builder extends VerificationByUsernameAndPassword_Draft_Builder {
        }
    }

    public static class Builder extends VerificationByUsernameAndPassword_Builder {
    }
}
