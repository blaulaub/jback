package ch.patchcode.jback.securityEntities.verificationMeans;

import org.inferred.freebuilder.FreeBuilder;

import java.util.UUID;

@FreeBuilder
public abstract class VerificationByConsole implements VerificationMean {

    @Override
    public abstract UUID getId();

    @Override
    public <R> R accept(Visitor<R> registrationHandler) {

        return registrationHandler.visit(this);
    }


    @Override
    public Draft toNewDraft() {
        return new Draft();
    }

    public static class Draft implements VerificationMean.Draft {

        // boring - there are no parameters :-)

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    public static class Builder extends VerificationByConsole_Builder {
    }
}
