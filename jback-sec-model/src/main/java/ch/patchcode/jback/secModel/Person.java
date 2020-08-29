package ch.patchcode.jback.secModel;

import java.util.List;

/**
 * Some distinct person.
 */
public interface Person {

    /**
     * A person may have zero or more roles. A role usually links a person to an organisation and defines the person's role within
     * that organisation.
     *
     * @return list of roles, with role-based privileges
     */
    List<Role> getRoles();

    /**
     * A person may have privileges separate to those stemming from roles. E.g., a person may get the privilege to define new
     * organisations.
     *
     * @return list of privileges in addition to those implied by roles
     */
    List<Privilege> getExtraPrivileges();
}
