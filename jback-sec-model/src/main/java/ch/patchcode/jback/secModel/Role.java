package ch.patchcode.jback.secModel;

import java.util.List;

/**
 * Some role.
 */
public interface Role<
        TOrganisation extends Organisation,
        TPerson extends Person,
        TPrincipal extends Principal<TPerson, TPrivilege, TAuthenticationMean>,
        TAuthenticationMean extends AuthenticationMean,
        TPrivilege extends Privilege,
        TRole extends Role<TOrganisation, TPerson, TPrincipal, TAuthenticationMean, TPrivilege, TRole, TUser>,
        TUser extends User<TOrganisation, TPerson, TPrincipal, TAuthenticationMean, TPrivilege, TRole, TUser>
        > {

    /**
     * @return the person holding the role
     */
    TPerson getPerson();

    /**
     * @return the organisation the role is for
     */
    TOrganisation getOrganisation();

    /**
     * @return list of privileges granted by this role
     */
    List<TPrivilege> getPrivileges();
}
