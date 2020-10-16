package ch.patchcode.jback.api.session;

import ch.patchcode.jback.presentation.Perspective;
import ch.patchcode.jback.presentation.impl.SpringAuthentication;
import ch.patchcode.jback.securityEntities.Principal;
import ch.patchcode.jback.securityEntities.TemporaryAuthentication;
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
                public void visit(ch.patchcode.jback.securityEntities.PersonalAuthentication personalAuth) {
                    builder.setPerspective(Perspective.MEMBER);
                }

                @Override
                public void visit(TemporaryAuthentication temporaryAuth) {
                    builder.setPerspective(Perspective.ENROLLING);
                }
            });
        }

        return builder.build();
    }
}
