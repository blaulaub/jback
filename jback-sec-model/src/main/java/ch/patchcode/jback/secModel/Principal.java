package ch.patchcode.jback.secModel;

import java.util.List;

/**
 * Some person or entity that can be authorized.
 * <p>
 * An authorized principal can then impersonate a person, inheriting that person's details, roles and privileges.
 */
public interface Principal<
        TOrganisation extends Organisation,
        TPerson extends Person<TOrganisation, TPerson, TPrincipal, TPrivilege, TRole, TUser>,
        TPrincipal extends Principal<TOrganisation, TPerson, TPrincipal, TPrivilege, TRole, TUser>,
        TPrivilege extends Privilege,
        TRole extends Role<TOrganisation, TPerson, TPrincipal, TPrivilege, TRole, TUser>,
        TUser extends User<TOrganisation, TPerson, TPrincipal, TPrivilege, TRole, TUser>
        > {

    /**
     * Zero or more privileges that the (or usually: any) principal always has. E.g., a principal should be able to create at
     * least one person for himself to impersonate.
     *
     * @return list of privileges
     */
    List<TPrivilege> getBasicPrivileges();
}
