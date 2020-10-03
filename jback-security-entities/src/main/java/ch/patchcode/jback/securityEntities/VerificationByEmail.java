package ch.patchcode.jback.securityEntities;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public abstract class VerificationByEmail implements VerificationMean {

    public abstract String getEmailAddress();

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

    public static class Builder extends VerificationByEmail_Builder {
    }
}
