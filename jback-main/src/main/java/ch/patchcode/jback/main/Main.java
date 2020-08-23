package ch.patchcode.jback.main;

import ch.patchcode.jback.api.ApiConfiguration;
import ch.patchcode.jback.core.CoreConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"ch.patchcode.jback.main"})
@Import({ApiConfiguration.class, CoreConfiguration.class})
public class Main {

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

}
