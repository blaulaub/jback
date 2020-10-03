package ch.patchcode.jback.security.authentications;

import ch.patchcode.jback.secBase.secModelImpl.Authority;
import ch.patchcode.jback.secBase.secModelImpl.Person;
import ch.patchcode.jback.securityEntities.*;

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

    @Override
    public String getFirstName() {

        return firstName;
    }

    @Override
    public String getLastName() {

        return lastName;
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
                    public VerificationByUsernameAndPassword visit(VerificationByUsernameAndPassword.Draft draft) {
                        return new VerificationByUsernameAndPassword.Builder()
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
