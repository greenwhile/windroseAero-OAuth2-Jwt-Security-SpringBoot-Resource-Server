package ua.uhmc.avia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.uhmc.avia.handler.ResourceHandler;

@SpringBootApplication(scanBasePackages = {"ua.uhmc.avia.component", "ua.uhmc.avia.error", "ua.uhmc.avia.handler", "ua.uhmc.avia.service", "ua.uhmc.avia.controller", "ua.uhmc.avia.model", "ua.uhmc.avia.springsecurity.config"})
//@SpringBootApplication
public class AviaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AviaApplication.class, args);
    }

}
