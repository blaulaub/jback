package ch.patchcode.jback.jpa.personalAuthentications;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonalAuthenticationJpaRepository extends JpaRepository<PersonalAuthenticationJpa, UUID> {
}
