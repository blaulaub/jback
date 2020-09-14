package ch.patchcode.jback.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

/**
 * Being empty, this class disables the framework's usual error handling actions.
 */
@Controller
public class WebErrorController implements ErrorController {

    private final Environment env;

    @Autowired
    public WebErrorController(Environment env) {
        this.env = env;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorPath() {
        return env.getProperty("server.error.path");
    }
}
