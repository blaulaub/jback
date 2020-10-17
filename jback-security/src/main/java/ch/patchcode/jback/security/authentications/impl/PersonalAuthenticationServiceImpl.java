package ch.patchcode.jback.security.authentications.impl;

import ch.patchcode.jback.security.authentications.PersonalAuthenticationService;
import ch.patchcode.jback.securityEntities.authentications.PersonalAuthenticationRepository;
import ch.patchcode.jback.securityEntities.authentications.Principal;
import ch.patchcode.jback.securityEntities.authentications.SuperuserAuthentication;

import javax.inject.Inject;
import java.util.Optional;

public class PersonalAuthenticationServiceImpl implements PersonalAuthenticationService {

    private final PersonalAuthenticationRepository personalAuthenticationRepository;
    private final String adminUsername;
    private final String adminPassword;

    @Inject
    public PersonalAuthenticationServiceImpl(
            PersonalAuthenticationRepository personalAuthenticationRepository,
            String adminUsername, String adminPassword) {
        this.personalAuthenticationRepository = personalAuthenticationRepository;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Override
    public Optional<Principal> findByUserIdentification(String userIdentification) {

        if (adminUsername != null && !adminUsername.isBlank() && adminPassword != null && !adminPassword.isBlank()) {
            if (adminUsername.equals(userIdentification)) {
                return Optional.of(new SuperuserAuthentication(adminUsername, adminPassword));
            }
        }

        // TODO should the repository be that clever? userIdentification could be a username, phone number, etc;
        // TODO the policy to resolve that maybe should be here (or maybe not)
        return personalAuthenticationRepository.findByUserIdentification(userIdentification).map(it -> it);
    }
}
