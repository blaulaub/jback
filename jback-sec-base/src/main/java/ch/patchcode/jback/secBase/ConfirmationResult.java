package ch.patchcode.jback.secBase;

public enum ConfirmationResult {

    /**
     * Means: it worked.
     */
    CONFIRMED {
        @Override
        public void accept(Visitor visitor) {
            visitor.caseConfirmed();
        }
    },

    /**
     * Means: non-existing or outdated. Retry will not help.
     */
    NOT_FOUND {
        @Override
        public void accept(Visitor visitor) {
            visitor.caseNotFound();
        }
    },

    /**
     * Means: the input was wrong and did not match. Retry with correct input if possible.
     */
    MISMATCH {
        @Override
        public void accept(Visitor visitor) {
            visitor.caseMismatch();
        }
    };

    public abstract void accept(Visitor visitor);

    public interface Visitor {

        void caseConfirmed();

        void caseNotFound();

        void caseMismatch();
    }
}
