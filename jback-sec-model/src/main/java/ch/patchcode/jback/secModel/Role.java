package ch.patchcode.jback.secModel;

import java.util.List;

/**
 * Some role.
 */
public interface Role<
        TOrganisation extends Organisation,
        TPerson extends Person<TOrganisation, TPerson, TPrincipal, TPrivilege, TRole, TUser>,
        TPrincipal extends Principal<TPrivilege>,
        TPrivilege extends Privilege,
        TRole extends Role<TOrganisation, TPerson, TPrincipal, TPrivilege, TRole, TUser>,
        TUser extends User<TOrganisation, TPerson, TPrincipal, TPrivilege, TRole, TUser>
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
