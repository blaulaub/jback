package ch.patchcode.jback.core.impl;

import ch.patchcode.jback.core.RoleService;
import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.coreEntities.roles.Role;
import ch.patchcode.jback.coreEntities.roles.RoleRepository;

import javax.inject.Inject;
import java.util.List;

public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Inject
    public RoleServiceImpl(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getRolesFor(List<Person> persons) {

        return null;
    }
}
