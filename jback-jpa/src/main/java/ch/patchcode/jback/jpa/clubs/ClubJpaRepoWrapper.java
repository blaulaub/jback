package ch.patchcode.jback.jpa.clubs;

import ch.patchcode.jback.core.clubs.Club;
import ch.patchcode.jback.core.clubs.ClubRepository;
import ch.patchcode.jback.core.persons.Person;
import ch.patchcode.jback.jpa.persons.PersonJpaRepository;
import ch.patchcode.jback.security.secBaseImpl.VerificationMean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClubJpaRepoWrapper implements ClubRepository<VerificationMean> {

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
    public Optional<Club<VerificationMean>> findById(UUID id) {

        return clubJpaRepository.findById(id).map(ClubJpa::toDomain);
    }

    @Override
    public Club<VerificationMean> create(Club.Draft<VerificationMean> draft) {

        return clubJpaRepository.save(fromDomain(draft)).toDomain();
    }

    private ClubJpa fromDomain(Club.Draft<VerificationMean> draft) {

        ClubJpa club = new ClubJpa();
        club.setName(draft.getName());
        draft.getContact()
                .map(Person::getId)
                .map(personJpaRepository::findById)
                .map(it -> it.orElseThrow(EntityNotFoundException::new))
                .ifPresent(club::setContact);
        draft.getUrl()
                .map(URI::toString)
                .ifPresent(club::setUri);
        return club;
    }
}
