package com.tutallerPuntoCom.generation.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebugEnv implements CommandLineRunner {

    @Value("${DB_PASSWORD:NO_CARGADA}")
    private String pass;

    @Value("${DB_HOST:NO_CARGADA}")
    private String host;

    @Override
    public void run(String... args) {
        System.out.println("HOST=" + host + " PASS=" + pass);
    }
}
