package ch.patchcode.jback.secModel;

import java.util.List;

/**
 * Some distinct person.
 */
public interface Person<
        TOrganisation extends Organisation,
        TPerson extends Person<TOrganisation, TPerson, TPrincipal, TPrivilege, TRole, TUser>,
        TPrincipal extends Principal<TPrivilege>,
        TPrivilege extends Privilege,
        TRole extends Role<TOrganisation, TPerson, TPrincipal, TPrivilege, TRole, TUser>,
        TUser extends User<TOrganisation, TPerson, TPrincipal, TPrivilege, TRole, TUser>
        > {

    /**
     * Zero, one or more principals that can impersonate this person.
     *
     * @return list of principals that can take control over this person
     */
    List<TPrincipal> getPrincipals();

    /**
     * A person may have zero or more roles. A role usually links a person to an organisation and defines the person's role within
     * that organisation.
     *
     * @return list of roles, with role-based privileges
     */
    List<TRole> getRoles();

    /**
     * A person may have privileges separate to those stemming from roles. E.g., a person may get the privilege to define new
     * organisations.
     *
     * @return list of privileges in addition to those implied by roles
     */
    List<TPrivilege> getExtraPrivileges();
}
