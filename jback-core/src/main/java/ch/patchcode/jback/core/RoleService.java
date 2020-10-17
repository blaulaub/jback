package ch.patchcode.jback.core;

import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.coreEntities.roles.Role;

import java.util.List;

public interface RoleService {

    List<Role> getRolesFor(List<Person> persons);
}
