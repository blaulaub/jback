package ch.patchcode.jback.securityEntities.authentications;

import ch.patchcode.jback.coreEntities.Authority;
import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.securityEntities.verificationMeans.VerificationMean;
import org.inferred.freebuilder.FreeBuilder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.Collections.singletonList;

@FreeBuilder
public abstract class PersonalAuthentication implements Principal {

    public abstract UUID getId();

    public abstract Person getHolder();

    @Override
    public String getFirstName() {

        return getHolder().getFirstName();
    }

    @Override
    public String getLastName() {

        return getHolder().getLastName();
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

        return getHolder().getName();
    }

    // impl ch.patchcode.jback.secBase.secModelImpl.Principal


    @Override
    public abstract List<Person> getPersons();

    @Override
    public abstract List<VerificationMean> getMeans();

    @Override
    public List<Authority> getBasicPrivileges() {

        return singletonList(Authority.CAN_CREATE_CLIENT_PERSON);
    }

    public static class Builder extends PersonalAuthentication_Builder {

        @Override
        public PersonalAuthentication build() {

            var holderBuilder = getHolderBuilder();
            this.mutatePersons(persons -> {
                if (persons.stream().noneMatch(it -> Objects.equals(it.getId(), holderBuilder.getId()))) {
                    persons.add(holderBuilder);
                }
            });
            return super.build();
        }
    }

    @FreeBuilder
    public interface Draft {

        Person getHolder();

        List<VerificationMean.Draft> getMeans();

        class Builder extends PersonalAuthentication_Draft_Builder {
        }
    }
}
