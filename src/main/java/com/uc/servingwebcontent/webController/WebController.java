package com.uc.servingwebcontent.webController;

import com.uc.servingwebcontent.component.CvsParser;
import com.uc.servingwebcontent.component.SpringApplicationContext;
import com.uc.servingwebcontent.converter.Converter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @PostMapping("/")
    public Response cvs(@RequestBody Request request) {
        CvsParser parser = (CvsParser) SpringApplicationContext.getBean("cvsParser");
        Converter converter = new Converter(parser.convertMap, request.getFrom(), request.getTo());
        return converter.convert();
    }
}