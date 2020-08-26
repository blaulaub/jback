package ch.patchcode.jback.api.registration;

import java.util.UUID;

public class PendingRegistrationInfo {

    private UUID pendingRegistrationId;

    public static PendingRegistrationInfo of(UUID pendingRegistrationId) {

        var result = new PendingRegistrationInfo();
        result.setPendingRegistrationId(pendingRegistrationId);
        return result;
    }

    public UUID getPendingRegistrationId() {
        return pendingRegistrationId;
    }

    public void setPendingRegistrationId(UUID pendingRegistrationId) {
        this.pendingRegistrationId = pendingRegistrationId;
    }
}
