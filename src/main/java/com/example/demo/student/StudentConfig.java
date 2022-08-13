package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student nick = new Student("Nick",
                    LocalDate.of(2001, Month.NOVEMBER, 30), "nick.willamowski@gmx.de");
            Student isil = new Student("Isil",
                    LocalDate.of(2000, Month.DECEMBER, 28), "i.odabasi@icloud.com");
            repository.saveAll(List.of(nick, isil));
        };
    }
}
