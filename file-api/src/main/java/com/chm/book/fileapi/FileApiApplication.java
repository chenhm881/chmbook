package com.chm.book.fileapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@SpringBootApplication
public class FileApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApiApplication.class, args);
    }

}
