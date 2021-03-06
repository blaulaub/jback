package ch.patchcode.jback.presentation;

import ch.patchcode.jback.coreEntities.Authority;
import org.springframework.security.core.GrantedAuthority;

public enum ApiAuthority implements GrantedAuthority {

    CAN_CREATE_OWN_PERSON,

    CAN_CREATE_CLIENT_PERSON,

    CAN_CREATE_CLUB,

    CAN_ASSIGN_MEMBER,

    CAN_ASSIGN_ADMIN;

    @Override
    public String getAuthority() {
        return toString();
    }

    public static ApiAuthority of(Authority authority) {
        return authority.accept(new Authority.Visitor<>() {

            @Override
            public ApiAuthority caseCanCreateOwnPerson() {
                return CAN_CREATE_OWN_PERSON;
            }

            @Override
            public ApiAuthority caseCanCreateClientPerson() {
                return CAN_CREATE_CLIENT_PERSON;
            }

            @Override
            public ApiAuthority caseCanCreateClub() {
                return CAN_CREATE_CLUB;
            }

            @Override
            public ApiAuthority caseCanAssignMember() {
                return CAN_ASSIGN_MEMBER;
            }

            @Override
            public ApiAuthority caseCanAssignAdmin() {
                return CAN_ASSIGN_ADMIN;
            }
        });
    }
}
