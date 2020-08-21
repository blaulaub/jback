package ch.patchcode.jback.api.dojos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("dojos")
public class DojosController {

    @GetMapping("{id}")
    public Dojo getDojo(@PathVariable("id") UUID id) {

        throw new RuntimeException("not implemented");
    }
}
