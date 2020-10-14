package ch.patchcode.jback.secModel;

import java.util.List;

/**
 * Some role, taken by a particular {@link Person} in a particular {@link Organisation} with some particular {@link Privilege}s.
 */
public interface Role<
        TOrganisation extends Organisation,
        TPerson extends Person,
        TPrivilege extends Privilege> {

    /**
     * The {@link Person} holding this role.
     *
     * @return the {@link Person} holding this role
     */
    TPerson getPerson();

    /**
     * The {@link Organisation} this role is for.
     *
     * @return the {@link Organisation} this role is for
     */
    TOrganisation getOrganisation();

    /**
     * Zero or more {@link Privilege}s granted by this role.
     *
     * @return zero or more {@link Privilege}s granted by this role
     */
    List<TPrivilege> getPrivileges();
}
