package ch.patchcode.jback.api.registration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface VerificationCode {

    String getVerificationCode();

    @JsonCreator
    static VerificationCode of(@JsonProperty("verificationCode") String verificationCode) {

        return new Builder().setVerificationCode(verificationCode).build();
    }

    default ch.patchcode.jback.secBase.VerificationCode toDomain() {

        return  ch.patchcode.jback.secBase.VerificationCode.of(getVerificationCode());
    }

    class Builder extends VerificationCode_Builder {}
}
