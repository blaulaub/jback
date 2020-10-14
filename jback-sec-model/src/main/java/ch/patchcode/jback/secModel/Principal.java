package ch.patchcode.jback.secModel;

import java.util.List;

/**
 * Something that can be authenticated through some {@link AuthenticationMean}s and then has some {@link Privilege}s.
 * <p>
 * An authenticated principal can then impersonate one or more {@link Person}s.
 */
public interface Principal<
        TPerson extends Person,
        TPrivilege extends Privilege,
        TAuthenticationMean extends AuthenticationMean
        > {

    /**
     * One or more {@link Person}s that this principal can impersonate.
     *
     * @return one or more {@link Person}s that this principal can impersonate
     */
    List<TPerson> getPersons();

    /**
     * Zero or more {@link Privilege}s the principal has anyway, regardless of role.
     *
     * @return zero or more {@link Privilege}s the principal has anyway, regardless of role
     */
    List<TPrivilege> getBasicPrivileges();

    /**
     * One or more {@link AuthenticationMean}s the principal by be authenticated by.
     *
     * @return one or more {@link AuthenticationMean}s the principal by be authenticated by
     */
    List<TAuthenticationMean> getMeans();
}
