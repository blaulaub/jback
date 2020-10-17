package ch.patchcode.jback.presentation.impl;

import ch.patchcode.jback.coreEntities.Authority;
import ch.patchcode.jback.presentation.ApiAuthority;
import ch.patchcode.jback.securityEntities.authentications.Principal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpringAuthenticationTest {

    @Test
    @DisplayName("Authentication of principal without privileges returns no privileges")
    void authenticationOfPrincipalWithoutPrivilegesReturnsNoPrivileges() {

        // arrange
        Principal principal = mock(Principal.class);

        var auth = new SpringAuthentication<>(principal);

        // act
        var authorities = auth.getAuthorities();

        // assert
        assertTrue(authorities.isEmpty());
    }

    @Test
    @DisplayName("Authentication of principal with one privilege returns privilege")
    void authenticationOfPrincipalWithOnePrivilegeReturnsPrivilege() {

        // arrange
        Principal principal = mock(Principal.class);
        when(principal.getBasicPrivileges()).thenReturn(asList(Authority.CAN_CREATE_CLUB));

        var auth = new SpringAuthentication<>(principal);

        // act
        var authorities = auth.getAuthorities();

        // assert
        assertEquals(1, authorities.size());
        assertTrue(authorities.containsAll(asList(ApiAuthority.CAN_CREATE_CLUB)));
    }
}
