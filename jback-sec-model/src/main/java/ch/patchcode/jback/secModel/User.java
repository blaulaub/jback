package ch.patchcode.jback.secModel;

/**
 * Combines the two aspects of taking a {@link Role} linked to an authenticated {@link Principal}.
 * <p>
 * This is usually identifies a session's user.
 */
public interface User<
        TOrganisation extends Organisation,
        TPerson extends Person,
        TPrincipal extends Principal<TPerson, TPrivilege, TAuthenticationMean>,
        TAuthenticationMean extends AuthenticationMean,
        TPrivilege extends Privilege,
        TRole extends Role<TOrganisation, TPerson, TPrivilege>
        > {

    /**
     * The {@link Principal} by which the user is authenticated.
     *
     * @return the {@link Principal} by which the user is authenticated
     */
    TPrincipal getPrincipal();

    /**
     * The {@link Role} currently taken by the user.
     *
     * @return the {@link Role} currently taken by the user
     */
    TRole getRole();

    /**
     * The {@link Person} behind the current {@link Role}.
     *
     * @return the {@link Person} behind the current {@link Role}
     */
    default TPerson getPerson() {

        return getRole().getPerson();
    }
}
