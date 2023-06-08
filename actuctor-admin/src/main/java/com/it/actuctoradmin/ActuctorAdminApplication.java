package com.it.actuctoradmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class ActuctorAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActuctorAdminApplication.class, args);
    }

}
