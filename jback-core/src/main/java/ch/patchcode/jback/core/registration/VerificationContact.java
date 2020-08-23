package ch.patchcode.jback.core.registration;

import org.inferred.freebuilder.FreeBuilder;

public interface VerificationContact {

    @FreeBuilder
    interface EmailContact extends VerificationContact {

        String getEmailAddress();

        class Builder extends VerificationContact_EmailContact_Builder {}
    }

    @FreeBuilder
    interface SmsContact extends VerificationContact {

        String getPhoneNumber();

        class Builder extends VerificationContact_SmsContact_Builder {}
    }
}
