package ch.patchcode.jback.securityEntities.authentications;

import ch.patchcode.jback.coreEntities.Authority;
import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.securityEntities.verificationMeans.VerificationMean;
import org.inferred.freebuilder.FreeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static java.util.List.copyOf;
import static java.util.Objects.requireNonNull;

public final class PersonalAuthentication implements Principal, Serializable {

    private final UUID id;

    private final Person holder;

    private final List<Person> persons;

    private final List<VerificationMean> means;

    public PersonalAuthentication(UUID id, Person holder, List<Person> persons, List<VerificationMean> means) {

        var p = new ArrayList<>(persons);
        if (!p.contains(holder)) {
            p.add(holder);
        }

        this.id = requireNonNull(id);
        this.holder = requireNonNull(holder);
        this.persons = unmodifiableList(p);
        this.means = copyOf(means);
    }

    public UUID getId() {
        return id;
    }

    public Person getHolder() {
        return holder;
    }

    @Override
    public List<Person> getPersons() {
        return persons;
    }

    @Override
    public List<VerificationMean> getMeans() {
        return means;
    }

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

    @Override
    public List<Authority> getBasicPrivileges() {

        return singletonList(Authority.CAN_CREATE_CLIENT_PERSON);
    }

    @FreeBuilder
    public interface Draft {

        Person getHolder();

        List<VerificationMean.Draft> getMeans();

        class Builder extends PersonalAuthentication_Draft_Builder {
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalAuthentication that = (PersonalAuthentication) o;
        return id.equals(that.id) &&
                holder.equals(that.holder) &&
                persons.equals(that.persons) &&
                means.equals(that.means);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, holder, persons, means);
    }
}
