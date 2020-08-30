package ch.patchcode.jback.secModel;

/**
 * Combines the two aspects of an authenticated principal and an authorized user.
 * <p>
 * This is intended as the root object identifying the session's user.
 * <p>
 * Note: A user may switch the impersonated person (if more than one is available) while keeping the principal. This should happen
 * without re-authentication.
 */
public interface User<
        TOrganisation extends Organisation,
        TPerson extends Person<TOrganisation, TPerson, TPrincipal, TPrivilege, TRole, TUser>,
        TPrincipal extends Principal<TOrganisation, TPerson, TPrincipal, TPrivilege, TRole, TUser>,
        TPrivilege extends Privilege,
        TRole extends Role<TOrganisation, TPerson, TPrincipal, TPrivilege, TRole, TUser>,
        TUser extends User<TOrganisation, TPerson, TPrincipal, TPrivilege, TRole, TUser>
        > {

    /**
     * @return the principal by which the user is authenticated
     */
    TPrincipal getPrincipal();

    /**
     * @return the person currently impersonated by the user
     */
    TPerson getPerson();
}
