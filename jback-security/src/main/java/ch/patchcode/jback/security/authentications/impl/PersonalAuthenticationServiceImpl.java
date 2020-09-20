package ch.patchcode.jback.security.authentications.impl;

import ch.patchcode.jback.security.entities.PersonalAuthentication;
import ch.patchcode.jback.security.entities.PersonalAuthenticationRepository;
import ch.patchcode.jback.security.authentications.PersonalAuthenticationService;

import javax.inject.Inject;
import java.util.Optional;

public class PersonalAuthenticationServiceImpl implements PersonalAuthenticationService {

    private final PersonalAuthenticationRepository personalAuthenticationRepository;

    @Inject
    public PersonalAuthenticationServiceImpl(PersonalAuthenticationRepository personalAuthenticationRepository) {
        this.personalAuthenticationRepository = personalAuthenticationRepository;
    }

    @Override
    public Optional<PersonalAuthentication> findByUserIdentification(String userIdentification) {

        // TODO the repository should not be that clever
        return personalAuthenticationRepository.findByUserIdentification(userIdentification);
    }
}
