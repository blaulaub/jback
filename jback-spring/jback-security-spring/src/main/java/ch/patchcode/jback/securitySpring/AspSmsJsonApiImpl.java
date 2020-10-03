package ch.patchcode.jback.securitySpring;

import ch.patchcode.jback.security.registration.impl.AspSmsVerificationServiceImpl.AspSmsJsonApi;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.inferred.freebuilder.FreeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class AspSmsJsonApiImpl implements AspSmsJsonApi {

    private static final Logger LOG = LoggerFactory.getLogger(AspSmsJsonApiImpl.class);

    public static final String SEND_SIMPLE_TEXT_SMS_URL = "https://json.aspsms.com/SendSimpleTextSMS";

    private final String username;
    private final String password;

    public AspSmsJsonApiImpl(String username, String password) {

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("username");
        }

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("password");
        }

        this.username = username;
        this.password = password;
    }

    @Override
    public void SendSimpleTextSMS(String phoneNumber, String text) {

        RestTemplate restTemplate = new RestTemplate();

        SendSimpleTextSMSRequestBody body = new SendSimpleTextSMSRequestBody.Builder()
                .username(username)
                .password(password)
                .originator("JBACK")
                .addRecipients(phoneNumber)
                .messageText(text)
                .forceGsm7BBit(Boolean.TRUE)
                .build();

        ResponseEntity<SendSimpleTextSMSResponseBody> response
                = restTemplate
                .postForEntity(SEND_SIMPLE_TEXT_SMS_URL, body, SendSimpleTextSMSResponseBody.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("POST " + SEND_SIMPLE_TEXT_SMS_URL + " failed.");
        }

        LOG.info("sent SMS to {}, got {}", phoneNumber, response.getBody());
    }

    @FreeBuilder
    public interface SendSimpleTextSMSRequestBody {

        @JsonProperty("UserName")
        String username();

        @JsonProperty("Password")
        String password();

        @JsonProperty("Originator")
        String originator();

        @JsonProperty("Recipients")
        List<String> recipients();

        @JsonProperty("MessageText")
        String messageText();

        @JsonProperty("ForceGSM7bit")
        Boolean forceGsm7BBit();

        class Builder extends AspSmsJsonApiImpl_SendSimpleTextSMSRequestBody_Builder {
        }
    }

    @FreeBuilder
    public interface SendSimpleTextSMSResponseBody {

        @JsonProperty("StatusCode")
        String statusCode();

        @JsonProperty("StatusInfo")
        String statusInfo();

        @JsonCreator
        static SendSimpleTextSMSResponseBody of(
                @JsonProperty("StatusCode") String statusCode,
                @JsonProperty("StatusInfo") String statusInfo
        ) {

            return new Builder()
                    .statusCode(statusCode)
                    .statusInfo(statusInfo)
                    .build();
        }

        class Builder extends AspSmsJsonApiImpl_SendSimpleTextSMSResponseBody_Builder {
        }
    }
}
