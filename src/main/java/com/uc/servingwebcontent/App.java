package com.uc.servingwebcontent;

import com.uc.servingwebcontent.service.CvsParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements ApplicationRunner {
    @Autowired
    private CvsParser parser;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            parser.path = args.getSourceArgs()[0];
        } catch (Exception e) {
            System.out.println("File path not specified");
            e.printStackTrace();
            System.exit(0);
        }
        try {
            parser.parse();
        } catch (Exception e) {
            System.out.println("Invalid file");
            e.printStackTrace();
            System.exit(0);
        }
    }
}