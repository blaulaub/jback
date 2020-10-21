package ch.patchcode.jback.api.session;

import ch.patchcode.jback.presentation.Perspective;
import ch.patchcode.jback.presentation.impl.SpringAuthentication;
import ch.patchcode.jback.securityEntities.authentications.PersonalAuthentication;
import ch.patchcode.jback.securityEntities.authentications.Principal;
import ch.patchcode.jback.securityEntities.authentications.SuperuserAuthentication;
import ch.patchcode.jback.securityEntities.authentications.TemporaryAuthentication;
import org.springframework.security.core.Authentication;

public class SessionInfoBuilder {

    private final Authentication auth;
    private final SessionInfo.Builder builder;

    public SessionInfoBuilder(Authentication auth) {

        this.auth = auth;

        this.builder = new SessionInfo.Builder()
                .setAuthenticated(auth.isAuthenticated())
                .setPrincipalName(auth.getName())
                .setPerspective(Perspective.GUEST);
    }

    public SessionInfo build() {

        if (auth instanceof SpringAuthentication) {

            var springAuth = (SpringAuthentication<?>) auth;
            builder.setFirstName(springAuth.getFirstName())
                    .setLastName(springAuth.getLastName());

            springAuth.accept(new Principal.Visitor() {
                @Override
                public void visit(PersonalAuthentication personalAuth) {
                    builder.setPerspective(Perspective.MEMBER);
                    builder.setUserId(personalAuth.getHolder().getId());
                }

                @Override
                public void visit(TemporaryAuthentication temporaryAuth) {
                    builder.setPerspective(Perspective.ENROLLING);
                }

                @Override
                public void visit(SuperuserAuthentication superuserAuthentication) {
                    builder.setPerspective(Perspective.MEMBER);
                }
            });
        }

        return builder.build();
    }
}
