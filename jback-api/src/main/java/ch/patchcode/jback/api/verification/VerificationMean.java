package ch.patchcode.jback.api.verification;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = VerificationByConsole.class, name = VerificationByConsole.TYPE),
        @Type(value = VerificationByEmail.class, name = VerificationByEmail.TYPE),
        @Type(value = VerificationBySms.class, name = VerificationBySms.TYPE),
        @Type(value = VerificationByUsernameAndPassword.class, name = VerificationByUsernameAndPassword.TYPE)
})
@ApiModel(subTypes = {
        VerificationByConsole.class,
        VerificationByEmail.class,
        VerificationBySms.class,
        VerificationByUsernameAndPassword.class
})
public abstract class VerificationMean {

    @ApiModelProperty
    public abstract UUID getId();

    @ApiModelProperty
    public abstract String getType();

    public abstract ch.patchcode.jback.securityEntities.verificationMeans.VerificationMean toDomain();

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
    @JsonSubTypes({
            @Type(value = VerificationByConsole.Draft.class, name = VerificationByConsole.TYPE),
            @Type(value = VerificationByEmail.Draft.class, name = VerificationByEmail.TYPE),
            @Type(value = VerificationBySms.Draft.class, name = VerificationBySms.TYPE),
            @Type(value = VerificationByUsernameAndPassword.Draft.class, name = VerificationByUsernameAndPassword.TYPE)
    })
    @ApiModel(subTypes = {
            VerificationByConsole.Draft.class,
            VerificationByEmail.Draft.class,
            VerificationBySms.Draft.class,
            VerificationByUsernameAndPassword.Draft.class
    })
    public static abstract class Draft {

        @ApiModelProperty
        public abstract String getType();

        public abstract ch.patchcode.jback.securityEntities.verificationMeans.VerificationMean.Draft toDomain();
    }
}
