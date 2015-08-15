package org.cob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@EnableJms
public class CobApplication {

    public static void main(String[] args) {
        SpringApplication.run(CobApplication.class, args);
    }
}
