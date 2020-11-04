package ch.patchcode.jback.jpa.wrappers;

import ch.patchcode.jback.coreEntities.Dojo;
import ch.patchcode.jback.coreRepositories.DojoRepository;
import ch.patchcode.jback.jpa.entities.DojoJpa;
import ch.patchcode.jback.jpa.entities.DojoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DojoJpaRepoWrapper implements DojoRepository {

    private final DojoJpaRepository dojoJpaRepository;

    @Autowired
    public DojoJpaRepoWrapper(
            DojoJpaRepository dojoJpaRepository
    ) {
        this.dojoJpaRepository = dojoJpaRepository;
    }

    @Override
    public Optional<Dojo> findById(UUID id) {

        return dojoJpaRepository.findById(id).map(DojoJpa::toDomain);
    }

    @Override
    public Dojo create(Dojo.Draft draft) {

        return dojoJpaRepository.save(fromDomain(draft)).toDomain();
    }

    private DojoJpa fromDomain(Dojo.Draft draft) {

        throw new RuntimeException("not implemented");
    }
}
