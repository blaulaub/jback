package ch.patchcode.jback.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"ch.patchcode.jback.main", "ch.patchcode.jback.api"})
public class Main {

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

}
