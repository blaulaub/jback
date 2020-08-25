package ch.patchcode.jback.api.registration;

import java.util.UUID;

public class PendingRegistrationInfo {

    private UUID id;

    public static PendingRegistrationInfo of(UUID id) {

        var result = new PendingRegistrationInfo();
        result.setId(id);
        return result;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
