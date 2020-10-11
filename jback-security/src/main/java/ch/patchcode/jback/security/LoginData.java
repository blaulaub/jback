package ch.patchcode.jback.security;

import ch.patchcode.jback.securityEntities.VerificationMean;
import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface LoginData {

    String getUserIdentification();

    VerificationMean.Draft getVerificationMean();

    class Builder extends LoginData_Builder {}
}
