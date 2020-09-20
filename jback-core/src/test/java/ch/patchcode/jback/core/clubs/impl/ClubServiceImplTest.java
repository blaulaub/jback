package ch.patchcode.jback.core.clubs.impl;

import ch.patchcode.jback.core.entities.Club;
import ch.patchcode.jback.core.entities.ClubRepository;
import ch.patchcode.jback.secBase.VerificationMean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ClubServiceImplTest {

    @Mock
    private ClubRepository<VerificationMean> clubRepository;

    @InjectMocks
    private ClubServiceImpl<VerificationMean> service;

    @BeforeEach
    void SetUp() {
        initMocks(this);
    }

    @Test
    void getClub_thatExists_returnsClub() {

        // arrange
        var id = UUID.randomUUID();
        var expected = Optional.of(new Club.Builder<>().buildPartial());
        when(clubRepository.findById(eq(id))).thenReturn(expected);

        // act
        var actual = service.getClub(id);

        // assert
        assertEquals(expected, actual);
    }
}