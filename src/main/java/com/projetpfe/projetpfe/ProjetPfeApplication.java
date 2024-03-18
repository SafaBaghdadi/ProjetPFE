package com.projetpfe.projetpfe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.projetpfe.projetpfe")
public class ProjetPfeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetPfeApplication.class, args);
    }

}
