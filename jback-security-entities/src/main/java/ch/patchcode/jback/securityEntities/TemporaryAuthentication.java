package ch.patchcode.jback.securityEntities;

import ch.patchcode.jback.coreEntities.Authority;
import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.securityEntities.verificationMeans.*;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * This is the most stupid form of an authenticated user (beside the anonymous user). What we have here is
 * someone who can verify his identity via something given by {@link #getMeans()}.
 */
public class TemporaryAuthentication implements Principal {

    private final String firstName;
    private final String lastName;
    private final VerificationMean.Draft mean;

    public TemporaryAuthentication(String firstName, String lastName, VerificationMean.Draft mean) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.mean = mean;
    }

    public static TemporaryAuthentication of(String firstName, String lastName, VerificationMean.Draft mean) {

        return new TemporaryAuthentication(firstName, lastName, mean);
    }

    @Override
    public String getFirstName() {

        return firstName;
    }

    @Override
    public String getLastName() {

        return lastName;
    }

    @Override
    public void accept(Visitor visitor) {

        visitor.visit(this);

    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {

        return visitor.visit(this);
    }

    // impl java.security.Principal

    @Override
    public String getName() {

        return getFirstName() + " " + getLastName();
    }

    // impl ch.patchcode.jback.secBase.secModelImpl.Principal

    @Override
    public List<VerificationMean> getMeans() {

        var mean = this.mean.accept(
                new VerificationMean.Draft.Visitor<VerificationMean>() {

                    @Override
                    public VerificationByConsole visit(VerificationByConsole.Draft draft) {
                        return new VerificationByConsole.Builder()
                                .setId(UUID.randomUUID())
                                .build();
                    }

                    @Override
                    public VerificationByEmail visit(VerificationByEmail.Draft draft) {
                        return new VerificationByEmail.Builder()
                                .setId(UUID.randomUUID())
                                .setEmailAddress(draft.getEmailAddress())
                                .build();
                    }

                    @Override
                    public VerificationBySms visit(VerificationBySms.Draft draft) {
                        return new VerificationBySms.Builder()
                                .setId(UUID.randomUUID())
                                .setPhoneNumber(draft.getPhoneNumber())
                                .build();
                    }

                    @Override
                    public VerificationByPassword visit(VerificationByPassword.Draft draft) {
                        return new VerificationByPassword.Builder()
                                .setId(UUID.randomUUID())
                                .setUsername(draft.getUsername())
                                .setPassword(draft.getPassword())
                                .build();
                    }
                });

        return singletonList(mean);
    }

    @Override
    public List<Person> getPersons() {

        // no person exists yet
        return emptyList();
    }

    @Override
    public List<Authority> getBasicPrivileges() {

        return singletonList(Authority.CAN_CREATE_OWN_PERSON);
    }
}
