package ch.patchcode.jback.secModel;

import java.util.List;

/**
 * Some person or entity that can be authorized.
 * <p>
 * An authorized principal can then impersonate a person, inheriting that person's details, roles and privileges.
 */
public interface Principal<TPerson extends Person, TPrivilege extends Privilege> {

    /**
     * Zero, one or more persons that this principal can impersonate.
     *
     * @return list of principals that can take control over this person
     */
    List<TPerson> getPersons();

    /**
     * Zero or more privileges that the (or usually: any) principal always has. E.g., a principal should be able to create at
     * least one person for himself to impersonate.
     *
     * @return list of privileges
     */
    List<TPrivilege> getBasicPrivileges();
}
