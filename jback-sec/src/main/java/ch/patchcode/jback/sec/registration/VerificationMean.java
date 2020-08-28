package ch.patchcode.jback.sec.registration;

import org.inferred.freebuilder.FreeBuilder;

public interface VerificationMean {

    <R> R accept(VerificationMeanVisitor<R> registrationHandler);

    class VerificationByConsole implements VerificationMean {

        // boring - there are no parameters :-)

        @Override
        public <R> R accept(VerificationMeanVisitor<R> registrationHandler) {

            return registrationHandler.visit(this);
        }
    }

    @FreeBuilder
    abstract class VerificationByEmail implements VerificationMean {

        public abstract String getEmailAddress();

        @Override
        public <R> R accept(VerificationMeanVisitor<R> registrationHandler) {

            return registrationHandler.visit(this);
        }

        public static class Builder extends VerificationMean_VerificationByEmail_Builder {}
    }

    @FreeBuilder
    abstract class VerificationBySms implements VerificationMean {

        public abstract String getPhoneNumber();

        @Override
        public <R> R accept(VerificationMeanVisitor<R> registrationHandler) {

            return registrationHandler.visit(this);
        }

        public static class Builder extends VerificationMean_VerificationBySms_Builder {}
    }

    interface VerificationMeanVisitor<R> {

        R visit(VerificationByConsole verificationByConsole);

        R visit(VerificationByEmail verificationByEmail);

        R visit(VerificationBySms verificationBySms);
    }
}
