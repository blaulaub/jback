package ch.patchcode.jback.api.session;

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

    public enum Kind {
        SUCCESS,
        NEED_CONFIRMATION_CODE,
        UNKNOWN_USER
    }

    public static class Builder extends LoginResponse_Builder {
    }
}
