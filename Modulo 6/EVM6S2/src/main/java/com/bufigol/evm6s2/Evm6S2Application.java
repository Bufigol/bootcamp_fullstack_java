package com.bufigol.evm6s2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.bufigol.evm6s2",
        "com.bufigol.modelo",
        "com.bufigol.interfaces"
})
public class Evm6S2Application {
    public static void main(String[] args) {
        SpringApplication.run(Evm6S2Application.class, args);
    }
}