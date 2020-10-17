package ch.patchcode.jback.jpa.wrappers;

import ch.patchcode.jback.coreEntities.roles.Role;
import ch.patchcode.jback.coreEntities.roles.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RoleJpaWrapper implements RoleRepository {

    @Override
    public Optional<Role> findById(UUID id) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public Role create(Role.Draft draft) {
        throw new RuntimeException("not implemented");
    }
}
