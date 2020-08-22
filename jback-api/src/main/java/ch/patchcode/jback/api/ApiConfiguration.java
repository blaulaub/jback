package ch.patchcode.jback.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration that automatically contains all controllers (and other
 * components) of this package and below through component scan.
 */
@Configuration
@ComponentScan
public class ApiConfiguration {
}
