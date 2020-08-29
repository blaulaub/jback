package ch.patchcode.jback.secModel;

import java.util.List;

/**
 * Some role.
 */
public interface Role {

    /**
     * @return the person holding the role
     */
    Person getPerson();

    /**
     * @return the organisation the role is for
     */
    Organisation getOrganisation();

    /**
     * @return list of privileges granted by this role
     */
    List<Privilege> getPrivileges();
}
