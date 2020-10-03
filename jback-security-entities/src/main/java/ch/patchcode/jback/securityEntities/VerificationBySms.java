package ch.patchcode.jback.securityEntities;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public abstract class VerificationBySms implements VerificationMean {

    public abstract String getPhoneNumber();

    @Override
    public <R> R accept(Visitor<R> registrationHandler) {

        return registrationHandler.visit(this);
    }

    @FreeBuilder
    public abstract static class Draft implements VerificationMean.Draft {

        public abstract String getPhoneNumber();

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }

        public static class Builder extends VerificationBySms_Draft_Builder {
        }
    }

    public static class Builder extends VerificationBySms_Builder {
    }
}
