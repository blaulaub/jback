package ch.patchcode.jback.api.exceptions;

import ch.patchcode.jback.core.clubs.ClubMembershipApplicationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {

    public NotFoundException() {
    }

    public NotFoundException(ClubMembershipApplicationNotFoundException e) {
        super(e);
    }
}
