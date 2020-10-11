package ch.patchcode.jback.security.authentications.impl;

import ch.patchcode.jback.securityEntities.PersonalAuthentication;
import ch.patchcode.jback.securityEntities.PersonalAuthenticationRepository;
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

        // TODO should the repository be that clever? userIdentification could be a username, phone number, etc;
        // TODO the policy to resolve that maybe should be here (or maybe not)
        return personalAuthenticationRepository.findByUserIdentification(userIdentification);
    }
}
