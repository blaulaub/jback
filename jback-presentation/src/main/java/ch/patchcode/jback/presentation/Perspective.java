package ch.patchcode.jback.presentation;

/**
 * Some distinct perspective on the app domain.
 * <p>
 * A perspective determines what a user gets presented in the app domain.
 * <p>
 * A person may play several roles, but while using the app, it will
 * usually have to pick a distinct role, and thus gain a distinct
 * perspective.
 */
public enum Perspective {

    /**
     * For unauthenticated strangers, visitors, guests.
     */
    GUEST,

    /**
     * For someone who started enrolling.
     */
    ENROLLING,

    /**
     * For any regular member.
     */
    MEMBER
}
