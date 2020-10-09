package ch.patchcode.jback.main;

import ch.patchcode.jback.api.ApiConfiguration;
import ch.patchcode.jback.core.CoreConfiguration;
import ch.patchcode.jback.jpa.entitiesSpring.JpaEntitiesConfiguration;
import ch.patchcode.jback.jpa.wrappers.JpaWrapperConfiguration;
import ch.patchcode.jback.presentation.PresentationConfiguration;
import ch.patchcode.jback.securitySpring.SecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration(exclude= {UserDetailsServiceAutoConfiguration.class})
@ComponentScan(basePackages = {"ch.patchcode.jback.main"})
@Import({
        ApiConfiguration.class,
        JpaWrapperConfiguration.class,
        JpaEntitiesConfiguration.class,
        PresentationConfiguration.class,
        CoreConfiguration.class,
        SecurityConfiguration.class
})
public class Main {

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

}
