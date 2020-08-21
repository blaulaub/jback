package ch.patchcode.jback.api.clubs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("clubs")
public class ClubsController {

    @GetMapping("{id}")
    public Club getClub(@PathVariable("id") UUID id) {

        throw new RuntimeException("not implemented");
    }
}
