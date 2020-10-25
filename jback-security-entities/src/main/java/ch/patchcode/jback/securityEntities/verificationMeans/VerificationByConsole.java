package ch.patchcode.jback.securityEntities.verificationMeans;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public final class VerificationByConsole implements VerificationMean, Serializable {

    private final UUID id;

    public VerificationByConsole(UUID id) {
        this.id = requireNonNull(id);
    }

    @Override
    public UUID getId() {
        return id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationByConsole that = (VerificationByConsole) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
