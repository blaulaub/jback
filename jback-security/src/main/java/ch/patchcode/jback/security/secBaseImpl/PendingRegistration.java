package ch.patchcode.jback.security.secBaseImpl;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface PendingRegistration extends ch.patchcode.jback.secBase.PendingRegistration {

    class Builder extends PendingRegistration_Builder {
    }
}
