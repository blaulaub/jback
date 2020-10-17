package ch.patchcode.jback.jpa.wrappers;

import ch.patchcode.jback.coreEntities.roles.Role;
import ch.patchcode.jback.coreEntities.roles.RoleRepository;
import ch.patchcode.jback.jpa.entities.RoleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RoleJpaWrapper implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;

    @Autowired
    public RoleJpaWrapper(RoleJpaRepository roleJpaRepository) {
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public Optional<Role> findById(UUID id) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public Role create(Role.Draft draft) {
        throw new RuntimeException("not implemented");
    }
}
