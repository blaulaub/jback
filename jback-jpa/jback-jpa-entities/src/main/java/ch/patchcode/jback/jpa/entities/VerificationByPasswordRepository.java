package ch.patchcode.jback.jpa.entities;

import java.util.List;
import ch.patchcode.jback.jpa.entities.VerificationMeanJpa.PasswordVerification;

public interface VerificationByPasswordRepository {

    <S extends PasswordVerification> List<S> findByUsername(String username);
}
