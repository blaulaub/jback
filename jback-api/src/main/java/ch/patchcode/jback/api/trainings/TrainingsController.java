package ch.patchcode.jback.api.trainings;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("trainings")
public class TrainingsController {

    @GetMapping("{id}")
    public Training getTraining(@PathVariable("id") UUID id) {

        throw new RuntimeException("not implemented");
    }
}
