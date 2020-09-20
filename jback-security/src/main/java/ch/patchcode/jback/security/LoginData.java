package ch.patchcode.jback.security;

import ch.patchcode.jback.security.entities.VerificationMean;
import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface LoginData {

    String getUserIdentification();

    VerificationMean getVerificationMean();

    class Builder extends LoginData_Builder {};
}
