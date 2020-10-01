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
}
