package ch.patchcode.jback.api.util;

import ch.patchcode.jback.presentation.impl.TemporaryAuthentication;
import ch.patchcode.jback.security.secBaseImpl.VerificationMean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@WithSecurityContext(factory = WithTemporaryAuthentication.Factory.class)
public @interface WithTemporaryAuthentication {

    String firstName() default "John";

    String lastName() default "Doe";

    class Factory implements WithSecurityContextFactory<WithTemporaryAuthentication> {

        @Override
        public SecurityContext createSecurityContext(WithTemporaryAuthentication annotation) {

            return new SecurityContext() {

                private final TemporaryAuthentication auth = new TemporaryAuthentication(
                        annotation.firstName(),
                        annotation.lastName(),
                        new VerificationMean.VerificationByConsole());

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
