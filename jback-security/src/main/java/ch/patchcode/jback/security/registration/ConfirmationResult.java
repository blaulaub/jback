package ch.patchcode.jback.security.registration;

// todo: implement visitor to prevent switch-case or if-if-if
public enum ConfirmationResult {

    /** Means: it worked. */
    CONFIRMED,

    /** Means: non-existing or outdated. Retry will not help. */
    NOT_FOUND,

    /** Means: the input was wrong and did not match. Retry with correct input if possible. */
    MISMATCH;
}