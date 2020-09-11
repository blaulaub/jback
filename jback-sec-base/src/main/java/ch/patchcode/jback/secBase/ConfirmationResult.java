package ch.patchcode.jback.secBase;

public enum ConfirmationResult {

    /**
     * Means: it worked.
     */
    CONFIRMED {
        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.caseConfirmed();
        }
    },

    /**
     * Means: non-existing or outdated. Retry will not help.
     */
    NOT_FOUND {
        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.caseNotFound();
        }
    },

    /**
     * Means: the input was wrong and did not match. Retry with correct input if possible.
     */
    MISMATCH {
        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.caseMismatch();
        }
    };

    public abstract <R> R accept(Visitor<R> visitor);

    public interface Visitor<R> {

        R caseConfirmed();

        R caseNotFound();

        R caseMismatch();
    }
}
