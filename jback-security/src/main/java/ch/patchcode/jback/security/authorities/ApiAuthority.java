package ch.patchcode.jback.security.authorities;

import ch.patchcode.jback.secBase.secModelImpl.Authority;
import org.springframework.security.core.GrantedAuthority;

public enum ApiAuthority implements GrantedAuthority {

    CAN_CREATE_PERSON;

    @Override
    public String getAuthority() {
        return toString();
    }

    public static ApiAuthority of(Authority authority) {
        return authority.accept(new Authority.Visitor<ApiAuthority>() {
            @Override
            public ApiAuthority caseCanCreatePerson() {
                return CAN_CREATE_PERSON;
            }
        });
    }
}
