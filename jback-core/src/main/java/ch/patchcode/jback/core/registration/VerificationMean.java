package ch.patchcode.jback.core.registration;

import org.inferred.freebuilder.FreeBuilder;

public interface VerificationMean {

    @FreeBuilder
    interface VerificationByEmail extends VerificationMean {

        String getEmailAddress();

        class Builder extends VerificationMean_VerificationByEmail_Builder {}
    }

    @FreeBuilder
    interface VerificationBySms extends VerificationMean {

        String getPhoneNumber();

        class Builder extends VerificationMean_VerificationBySms_Builder {}
    }
}
