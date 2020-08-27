package ch.patchcode.jback.core.registration;

public enum ConfirmationResult {

    SUCCESS,

    // implies no retry
    NOT_FOUND,

    // implies retry
    WRONG_CODE;
}
