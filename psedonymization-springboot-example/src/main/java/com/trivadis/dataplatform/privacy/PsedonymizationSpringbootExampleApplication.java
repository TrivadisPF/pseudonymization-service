package com.trivadis.dataplatform.privacy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PsedonymizationSpringbootExampleApplication implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(PsedonymizationSpringbootExampleApplication.class);

    @Value("${secretKey}")
    String secretKey;

    @Autowired
    private Controller controller;

    public static void main(String[] args) {
        SpringApplication.run(PsedonymizationSpringbootExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        controller.doIt("Hello Spring with Data Privacy!");
        System.out.println(secretKey);
    }

}
