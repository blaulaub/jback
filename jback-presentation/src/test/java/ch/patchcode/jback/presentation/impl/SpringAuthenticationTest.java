package ch.patchcode.jback.presentation.impl;

import ch.patchcode.jback.coreEntities.Authority;
import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.coreEntities.roles.Role;
import ch.patchcode.jback.presentation.ApiAuthority;
import ch.patchcode.jback.securityEntities.authentications.Principal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
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
        when(principal.getBasicPrivileges()).thenReturn(asList(Authority.CAN_CREATE_CLIENT_PERSON));

        var auth = new SpringAuthentication<>(principal);

        // act
        var authorities = auth.getAuthorities();

        // assert
        assertEquals(1, authorities.size());
        assertTrue(authorities.containsAll(asList(ApiAuthority.CAN_CREATE_CLIENT_PERSON)));
    }

    @Test
    @DisplayName("Authentication of principal and role without matching person throws exception")
    void authenticationOfPrincipalAndRoleWithoutMatchingPersonThrowsException() {

        // arrange
        Principal principal = mock(Principal.class);
        Role role = mock(Role.class);

        // act & assert
        assertThrows(IllegalArgumentException.class, () -> new SpringAuthentication<>(principal, role));
    }

    @Test
    @DisplayName("Authentication of principal with role with one privilege returns privilege")
    void authenticationOfPrincipalWithRoleWithOnePrivilegeReturnsPrivilege() {

        // arrange
        Person person = mock(Person.class);

        Principal principal = mock(Principal.class);
        when(principal.getPersons()).thenReturn(asList(person));

        Role role = mock(Role.class);
        when(role.getPerson()).thenReturn(person);
        when(role.getPrivileges()).thenReturn(asList(Authority.CAN_CREATE_CLUB));

        var auth = new SpringAuthentication<>(principal, role);

        // act
        var authorities = auth.getAuthorities();

        // assert
        assertEquals(1, authorities.size());
        assertTrue(authorities.containsAll(asList(ApiAuthority.CAN_CREATE_CLUB)));
    }

    @Test
    @DisplayName("Authentication of principal with role with overlapping privileges returns unique privileges")
    void authenticationOfPrincipalWithRoleWithOverlappingPrivilegesReturnsUniquePrivileges() {

        // arrange
        Person person = mock(Person.class);

        Principal principal = mock(Principal.class);
        when(principal.getPersons()).thenReturn(asList(person));
        when(principal.getBasicPrivileges()).thenReturn(asList(
                Authority.CAN_CREATE_CLIENT_PERSON,
                Authority.CAN_CREATE_OWN_PERSON
        ));

        Role role = mock(Role.class);
        when(role.getPerson()).thenReturn(person);
        when(role.getPrivileges()).thenReturn(asList(
                Authority.CAN_CREATE_CLUB,
                Authority.CAN_CREATE_OWN_PERSON
        ));

        var auth = new SpringAuthentication<>(principal, role);

        // act
        var authorities = auth.getAuthorities();

        // assert
        assertEquals(3, authorities.size());
        assertTrue(authorities.containsAll(asList(
                ApiAuthority.CAN_CREATE_CLIENT_PERSON,
                ApiAuthority.CAN_CREATE_OWN_PERSON,
                ApiAuthority.CAN_CREATE_CLUB
        )));
    }
}
