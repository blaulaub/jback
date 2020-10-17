package ch.patchcode.jback.securityEntities.authentications;

import ch.patchcode.jback.coreEntities.Authority;
import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.securityEntities.verificationMeans.VerificationByPassword;
import ch.patchcode.jback.securityEntities.verificationMeans.VerificationMean;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class SuperuserAuthentication implements Principal {

    private final VerificationByPassword verificationMean;

    public SuperuserAuthentication(String username, String password) {

        this.verificationMean = new VerificationByPassword.Builder()
                .setId(new UUID(0L, 0L))
                .setUsername(username)
                .setPassword(password)
                .build();
    }

    @Override
    public String getFirstName() {

        return verificationMean.getUsername();
    }

    @Override
    public String getLastName() {

        return "";
    }

    @Override
    public void accept(Visitor visitor) {

        visitor.visit(this);
    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {

        return visitor.visit(this);
    }

    @Override
    public String getName() {

        return verificationMean.getUsername();
    }

    @Override
    public List<VerificationMean> getMeans() {

        return singletonList(verificationMean);
    }

    @Override
    public List<Person> getPersons() {

        // maybe this should be all persons instead
        return emptyList();
    }

    @Override
    public List<Authority> getBasicPrivileges() {

        return asList(Authority.values());
    }
}
