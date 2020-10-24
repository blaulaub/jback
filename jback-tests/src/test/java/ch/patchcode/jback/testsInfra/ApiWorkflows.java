package ch.patchcode.jback.testsInfra;

import ch.patchcode.jback.api.persons.PersonWithPasswordDraft;
import ch.patchcode.jback.api.registration.InitialRegistrationData;
import ch.patchcode.jback.api.registration.PendingRegistrationInfo;
import ch.patchcode.jback.api.session.LoginData;
import ch.patchcode.jback.api.verification.VerificationByUsernameAndPassword;
import ch.patchcode.jback.api.verification.VerificationCode;
import org.springframework.core.env.Environment;

import static ch.patchcode.jback.testsInfra.ConstantVerificationCodeProvider.VERIFICATION_CODE;
import static ch.patchcode.jback.testsInfra.Some.initialRegistrationData;

/**
 * A bunch of pre-defined, standard workflows, for your convenience.
 */
public final class ApiWorkflows {

    private final Api api;
    private final Environment env;

    public ApiWorkflows(Api api, Environment env) {
        this.api = api;
        this.env = env;
    }

    public Api.CallResult registerWithCode(InitialRegistrationData content, String code) throws Exception {

        var info = api.postRegistration(content)
                .andAssumeGoodAndReturn(PendingRegistrationInfo.class);

        return api.putVerificationCode(
                info.getPendingRegistrationId(),
                VerificationCode.of(code)
        );
    }


    public Api.CallResult registerAndPostMeToPersons(PersonWithPasswordDraft content) throws Exception {

        InitialRegistrationData initialData = InitialRegistrationData.Builder
                .from(initialRegistrationData())
                .setFirstName(content.getFirstName())
                .setLastName(content.getLastName())
                .build();

        var info = api.postRegistration(initialData)
                .andAssumeGoodAndReturn(PendingRegistrationInfo.class);

        api.putVerificationCode(
                info.getPendingRegistrationId(),
                VerificationCode.of(VERIFICATION_CODE)
        ).andAssumeGoodAndReturn();

        return api.postPersonWithPassword(content);
    }

    public Api.CallResult loginAsSuperuser() throws Exception {

        String username = env.getProperty("ADMIN_USERNAME");
        String password = env.getProperty("ADMIN_PASSWORD");

        var loginData = new LoginData.Builder()
                .setUserIdentification(username)
                .setVerificationMean(VerificationByUsernameAndPassword.Draft.create(username, password))
                .build();

        return api.postLogin(loginData);
    }

    public Api.CallResult loginAsMeBy(PersonWithPasswordDraft data) throws Exception {

        var loginData = new LoginData.Builder()
                .setUserIdentification(data.getUsername())
                .setVerificationMean(VerificationByUsernameAndPassword.Draft.create(
                        data.getUsername(),
                        data.getPassword()))
                .build();
        return api.postLogin(loginData);
    }
}
