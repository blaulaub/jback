package ch.patchcode.jback.api.util;

import ch.patchcode.jback.core.entities.Person;
import ch.patchcode.jback.presentation.impl.PersonalAuthentication;
import ch.patchcode.jback.security.entities.VerificationMean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.lang.annotation.*;
import java.util.UUID;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@WithSecurityContext(factory = WithPersonalAuthentication.Factory.class)
public @interface WithPersonalAuthentication {

    String uuid() default "37044090-bcb4-484c-9bb7-e4281b0c960d";

    String firstName() default "Jane";

    String lastName() default "Doe";

    class Factory implements WithSecurityContextFactory<WithPersonalAuthentication> {

        @Override
        public SecurityContext createSecurityContext(WithPersonalAuthentication annotation) {

            return new SecurityContext() {

                private final PersonalAuthentication auth = new PersonalAuthentication.Builder()
                        .setHolder(new Person.Builder<VerificationMean>()
                                .setId(UUID.fromString(annotation.uuid()))
                                .setFirstName(annotation.firstName())
                                .setLastName(annotation.lastName())
                                .build())
                        .addMeans(new VerificationMean.VerificationByConsole())
                        .build();

                @Override
                public Authentication getAuthentication() {

                    return auth;
                }

                @Override
                public void setAuthentication(Authentication authentication) {

                    throw new RuntimeException("immutable");
                }
            };
        }
    }
}
