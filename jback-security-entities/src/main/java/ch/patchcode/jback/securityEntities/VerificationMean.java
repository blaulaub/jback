package ch.patchcode.jback.securityEntities;

import org.inferred.freebuilder.FreeBuilder;

public interface VerificationMean extends ch.patchcode.jback.secBase.VerificationMean {

    <R> R accept(Visitor<R> registrationHandler);

    class VerificationByConsole implements VerificationMean {

        // boring - there are no parameters :-)

        @Override
        public <R> R accept(Visitor<R> registrationHandler) {

            return registrationHandler.visit(this);
        }
    }

    @FreeBuilder
    abstract class VerificationByEmail implements VerificationMean {

        public abstract String getEmailAddress();

        @Override
        public <R> R accept(Visitor<R> registrationHandler) {

            return registrationHandler.visit(this);
        }

        public static class Builder extends VerificationMean_VerificationByEmail_Builder {}
    }

    @FreeBuilder
    abstract class VerificationBySms implements VerificationMean {

        public abstract String getPhoneNumber();

        @Override
        public <R> R accept(Visitor<R> registrationHandler) {

            return registrationHandler.visit(this);
        }

        public static class Builder extends VerificationMean_VerificationBySms_Builder {}
    }

    @FreeBuilder
    abstract class VerificationByUsernameAndPassword implements VerificationMean {

        public abstract String getUsername();

        public abstract String getPassword();

        @Override
        public <R> R accept(Visitor<R> registrationHandler) {

            return registrationHandler.visit(this);
        }

        public static class Builder extends VerificationMean_VerificationByUsernameAndPassword_Builder {}
    }

    interface Visitor<R> {

        R visit(VerificationMean.VerificationByConsole verificationByConsole);

        R visit(VerificationMean.VerificationByEmail verificationByEmail);

        R visit(VerificationMean.VerificationBySms verificationBySms);

        R visit(VerificationByUsernameAndPassword verificationByUsernameAndPassword);
    }
}
