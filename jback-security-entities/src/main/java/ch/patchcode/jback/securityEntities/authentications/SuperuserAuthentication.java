package ch.patchcode.jback.securityEntities.authentications;

import ch.patchcode.jback.coreEntities.Authority;
import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.securityEntities.verificationMeans.VerificationByPassword;
import ch.patchcode.jback.securityEntities.verificationMeans.VerificationMean;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public final class SuperuserAuthentication implements Principal, Serializable {

    private final VerificationByPassword verificationMean;

    public SuperuserAuthentication(String username, String password) {

        this.verificationMean = new VerificationByPassword(
                new UUID(0L, 0L),
                username,
                password
        );
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuperuserAuthentication that = (SuperuserAuthentication) o;
        return verificationMean.equals(that.verificationMean);
    }

    @Override
    public int hashCode() {
        return Objects.hash(verificationMean);
    }
}
