package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplicationTests implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplicationTests.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    // no startup logic needed
    System.out.println("hello");
  }
}
