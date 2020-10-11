package ch.patchcode.jback.jpa.entitiesSpring;

import ch.patchcode.jback.jpa.entities.VerificationMeanJpa.PasswordVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VerificationByPasswordRepository extends
        JpaRepository<PasswordVerification, UUID>,
        ch.patchcode.jback.jpa.entities.VerificationByPasswordRepository {

    @Override
    <S extends PasswordVerification> List<S> findByUsername(String username);
}
