package ch.patchcode.jback.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * Spring configuration that automatically contains all controllers (and other
 * components) of this package and below through component scan. It does not
 * pre-configure any dependencies (services), these need to be configured
 * elsewhere.
 */
@Configuration
@ComponentScan
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApiConfiguration {
}
