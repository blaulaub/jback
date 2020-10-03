package ch.patchcode.jback.securityEntities;

import org.inferred.freebuilder.FreeBuilder;

public class VerificationByConsole implements VerificationMean {

    // boring - there are no parameters :-)

    @Override
    public <R> R accept(Visitor<R> registrationHandler) {

        return registrationHandler.visit(this);
    }

    @FreeBuilder
    public abstract static class Draft implements VerificationMean.Draft {

        // boring - there are no parameters :-)

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }

        public static class Builder extends VerificationByConsole_Draft_Builder {
        }
    }
}
