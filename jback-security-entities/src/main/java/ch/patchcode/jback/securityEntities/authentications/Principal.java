package ch.patchcode.jback.securityEntities.authentications;

import ch.patchcode.jback.coreEntities.Person;
import ch.patchcode.jback.coreEntities.Authority;
import ch.patchcode.jback.securityEntities.verificationMeans.VerificationMean;

import java.util.List;

public interface Principal extends
        java.security.Principal,
        ch.patchcode.jback.secModel.Principal<Person, Authority, VerificationMean>  {

    String getFirstName();

    String getLastName();

    void accept(Visitor visitor);

    <T> T accept(ResultVisitor<T> visitor);

    // impl java.security.Principal

    @Override
    String getName();

    @Override
    List<VerificationMean> getMeans();

    @Override
    List<Authority> getBasicPrivileges();

    interface Visitor {

        void visit(PersonalAuthentication personalAuthentication);

        void visit(TemporaryAuthentication temporaryAuthentication);

        void visit(SuperuserAuthentication superuserAuthentication);
    }

    interface ResultVisitor<T> {

        T visit(PersonalAuthentication personalAuthentication);

        T visit(TemporaryAuthentication temporaryAuthentication);

        T visit(SuperuserAuthentication superuserAuthentication);
    }
}
