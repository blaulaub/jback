package ch.patchcode.jback.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration that automatically contains all services (and other
 * components) of this package and below through component scan. It does not
 * pre-configure any dependencies (repositories), these need to be configured
 * elsewhere.
 */
@Configuration
@ComponentScan
public class SecConfiguration {

}
