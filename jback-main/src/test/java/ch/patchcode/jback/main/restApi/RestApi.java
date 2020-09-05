package ch.patchcode.jback.main.restApi;

import ch.patchcode.jback.api.registration.InitialRegistrationData;
import ch.patchcode.jback.api.registration.PendingRegistrationInfo;
import ch.patchcode.jback.api.registration.VerificationCode;
import ch.patchcode.jback.api.session.SessionInfo;
import ch.patchcode.jback.main.util.RestSession;

public class RestApi {

    private final RestSession restSession;

    public RestApi(RestSession restSession) {
        this.restSession = restSession;
    }

    public RegistrationPostData registrationPostData(InitialRegistrationData initialRegistrationData) throws Exception {
        return new RegistrationPostData(restSession.post(
                "/api/v1/registration",
                initialRegistrationData,
                PendingRegistrationInfo.class
        ));
    }

    public RegistrationPutCode registrationPutCode(
            PendingRegistrationInfo registrationInfo,
            VerificationCode code
    ) throws Exception {
        return new RegistrationPutCode(restSession.put(
                "/api/v1/registration/" + registrationInfo.getPendingRegistrationId(),
                code
        ));
    }

    public SessionGet sessionGet() {
        return new SessionGet(restSession.get(
                "/api/v1/session/",
                SessionInfo.class
        ));
    }

    public SessionPostLogout sessionPostLogout() throws Exception {
        return new SessionPostLogout(restSession.post(
                "/api/v1/session/logout",
                null,
                Void.class
        ));

    }
}
