package ch.patchcode.jback.jpa.principals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrincipalJpaRepository extends JpaRepository<PrincipalJpa, UUID> {
}
