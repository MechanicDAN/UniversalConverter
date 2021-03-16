package com.uc.servingwebcontent;


import com.uc.servingwebcontent.component.CvsParser;
import com.uc.servingwebcontent.component.SpringApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        CvsParser parser =(CvsParser) SpringApplicationContext.getBean("cvsParser");
        try {
            parser.setPath(args[0]);
            parser.parse();
        } catch (Exception e) {
            System.out.println("Не указан путь к файлу");
            e.printStackTrace();
            System.exit(0);
        }
    }
}