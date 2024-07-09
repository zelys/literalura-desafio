package com.desafio.literalura.config;

import com.desafio.literalura.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LiteraluraAppRunner implements CommandLineRunner {
    private Principal principal;

    public LiteraluraAppRunner(Principal principal) {
        this.principal = principal;
    }

    @Override
    public void run(String... args) throws Exception {
        principal.mostrarMenu();
    }
}
