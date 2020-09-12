package ch.patchcode.jback.security.authentications;

import ch.patchcode.jback.secBase.VerificationMean;
import ch.patchcode.jback.secBase.secModelImpl.Authority;
import ch.patchcode.jback.security.Authentication;
import com.google.common.collect.ImmutableList;

import java.util.List;

import static java.util.Collections.singletonList;

public class PermanentAuthentication implements Authentication {

    private final ImmutableList<VerificationMean> means;

    // this may not be the final constructor
    public PermanentAuthentication(List<VerificationMean> means) {
        this.means = ImmutableList.copyOf(means);
    }

    // impl ch.patchcode.jback.secBase.secModelImpl.Principal

    @Override
    public List<VerificationMean> getMeans() {
        return means;
    }

    @Override
    public List<Authority> getBasicPrivileges() {

        return singletonList(Authority.CAN_CREATE_CLIENT_PERSON);
    }
}
