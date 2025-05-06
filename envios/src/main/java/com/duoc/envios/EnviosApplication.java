package com.duoc.envios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnviosApplication {
  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(EnviosApplication.class);
    app.setAdditionalProfiles("cloud");
    app.run(args);
  }
}
