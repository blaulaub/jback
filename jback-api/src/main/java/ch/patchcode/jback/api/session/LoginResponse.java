package ch.patchcode.jback.api.session;

import ch.patchcode.jback.presentation.TryLoginResult;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import org.inferred.freebuilder.FreeBuilder;

@ApiModel
@FreeBuilder
public abstract class LoginResponse {

    public abstract Kind getKind();

    @JsonCreator
    public static LoginResponse of(
            @JsonProperty(value = "kind", required = true) Kind kind) {

        return new Builder().setKind(kind).build();
    }

    public static LoginResponse fromDomain(TryLoginResult tryLogin) {

        return LoginResponse.of(tryLogin.accept(new TryLoginResult.VisitingProducer<>() {

            @Override
            public Kind caseSuccess() {
                return Kind.SUCCESS;
            }

            @Override
            public Kind caseNeedConfirmationCode() {
                return Kind.NEED_CONFIRMATION_CODE;
            }

            @Override
            public Kind caseUnknownUser() {
                return Kind.UNKNOWN_USER;
            }
        }));
    }

    public enum Kind {
        SUCCESS,
        NEED_CONFIRMATION_CODE,
        UNKNOWN_USER
    }

    public static class Builder extends LoginResponse_Builder {
    }
}
