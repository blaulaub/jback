package ch.patchcode.jback.secModel;

/**
 * Combines the two aspects of an authenticated principal and an authorized user.
 * <p>
 * This is intended as the root object identifying the session's user.
 * <p>
 * Note: A user may switch the impersonated person (if more than one is available) while keeping the principal. This should happen
 * without re-authentication.
 */
public interface User {

    /**
     * @return the principal by which the user is authenticated
     */
    Principal getPrincipal();

    /**
     * @return the person currently impersonated by the user
     */
    Person getPerson();
}
