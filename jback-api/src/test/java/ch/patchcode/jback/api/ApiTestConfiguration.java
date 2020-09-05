package ch.patchcode.jback.api;

import ch.patchcode.jback.api.fakeServices.FakeServicesConfiguration;
import ch.patchcode.jback.security.SecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Core configuration to run MVC tests on the controllers. This uses the
 * API configuration together with a configuration for fake services that
 * provide "some" output.
 */
@Configuration
@EnableWebMvc
@Import({ApiConfiguration.class, FakeServicesConfiguration.class})
public class ApiTestConfiguration {
}
