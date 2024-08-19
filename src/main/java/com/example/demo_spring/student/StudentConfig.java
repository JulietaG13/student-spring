package com.example.demo_spring.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class StudentConfig {
  
  @Bean
  CommandLineRunner commandLineRunner(StudentRepository repository) {
    return args -> {
      Student marian = new Student(
          "Mariam",
          "marian123@gmail.com",
          LocalDate.of(2000, JANUARY, 1)
      );
      Student alex = new Student(
          "Alex",
          "alex123@gmail.com",
          LocalDate.of(2000, FEBRUARY, 2)
      );
      
      repository.saveAll(
          List.of(
              marian,
              alex
          )
      );
    };
  }
}
