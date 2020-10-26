package ch.patchcode.jback.jpa.wrappers;

import ch.patchcode.jback.coreEntities.Club;
import ch.patchcode.jback.coreEntities.ClubRepository;
import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.jpa.entities.ClubJpa;
import ch.patchcode.jback.jpa.entities.ClubJpaRepository;
import ch.patchcode.jback.jpa.entities.PersonJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class ClubJpaRepoWrapper implements ClubRepository {

    private final ClubJpaRepository clubJpaRepository;
    private final PersonJpaRepository personJpaRepository;

    @Autowired
    public ClubJpaRepoWrapper(
            ClubJpaRepository clubJpaRepository,
            PersonJpaRepository personJpaRepository
    ) {
        this.clubJpaRepository = clubJpaRepository;
        this.personJpaRepository = personJpaRepository;
    }

    @Override
    public Optional<Club> findById(UUID id) {

        return clubJpaRepository.findById(id).map(ClubJpa::toDomain);
    }

    @Override
    public Club create(Club.Draft draft) {

        return clubJpaRepository.save(fromDomain(draft)).toDomain();
    }

    @Override
    public List<Club> findAllLimitedTo(int count) {

        return clubJpaRepository.findAllLimitedTo(count).stream()
                .map(ClubJpa::toDomain)
                .collect(toList());
    }

    @Override
    public List<Club> findByNameContainingLimitedTo(String pattern, int count) {

        return clubJpaRepository.findAllByNameContainingLimitedTo(pattern, count).stream()
                .map(ClubJpa::toDomain)
                .collect(toList());
    }

    private ClubJpa fromDomain(Club.Draft draft) {

        ClubJpa club = new ClubJpa();
        club.setName(draft.getName());
        draft.getContact().map(personJpaRepository::toJpaIfConsistent).ifPresent(club::setContact);
        draft.getUrl()
                .map(URI::toString)
                .ifPresent(club::setUri);
        return club;
    }
}
