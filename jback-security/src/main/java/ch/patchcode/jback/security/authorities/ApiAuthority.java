package ch.patchcode.jback.security.authorities;

import ch.patchcode.jback.secBase.secModelImpl.Authority;
import org.springframework.security.core.GrantedAuthority;

public enum ApiAuthority implements GrantedAuthority {

    CAN_CREATE_OWN_PERSON,

    CAN_CREATE_CLIENT_PERSON;

    @Override
    public String getAuthority() {
        return toString();
    }

    public static ApiAuthority of(Authority authority) {
        return authority.accept(new Authority.Visitor<ApiAuthority>() {

            @Override
            public ApiAuthority caseCanCreateOwnPerson() {
                return CAN_CREATE_OWN_PERSON;
            }

            @Override
            public ApiAuthority caseCanCreateClientPerson() {
                return CAN_CREATE_CLIENT_PERSON;
            }
        });
    }
}
