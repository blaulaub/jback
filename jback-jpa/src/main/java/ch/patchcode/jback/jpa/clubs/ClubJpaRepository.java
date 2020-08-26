package ch.patchcode.jback.jpa.clubs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClubJpaRepository extends JpaRepository<Club, UUID> {
}
