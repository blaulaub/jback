package ch.patchcode.jback.secModel;

/**
 * A mean by which a {@link Principal} can be authenticated.
 * <p>
 * Examples are: by password (then the {@link AuthenticationMean} should contain the password);
 * by a confirmation code sent by SMS (then the {@link AuthenticationMean} should contain the phone number)
 * or an email address (then the {@link AuthenticationMean} should contain the email address); and so on.
 */
public interface AuthenticationMean {
}
