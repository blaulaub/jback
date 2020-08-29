package ch.patchcode.jback.secModel;

import java.util.List;

/**
 * Some person or entity that can be authorized.
 * <p>
 * An authorized principal can then impersonate a person, inheriting that person's details, roles and privileges.
 */
public interface Principal {

    /**
     * Zero, one or more persons that this principal can impersonate. The list may be empty, but the principal may still have the
     * privilege to create a new person.
     * <p>
     * Rationale: A principal may be a parent, the persons may be the parent's children managed by the parent.
     * <p>
     * Note: Vice versa, a person may have more than one principal, e.g., a child usually has two parents.
     *
     * @return list of persons this principal may impersonate
     */
    List<Person> getPersons();

    /**
     * Zero or more privileges that the (or usually: any) principal always has. E.g., a principal should be able to create at
     * least one person for himself to impersonate.
     *
     * @return list of privileges
     */
    List<Privilege> getBasicPrivileges();
}
