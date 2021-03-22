package com.uc.servingwebcontent.webController;

import com.uc.servingwebcontent.dto.Request;
import com.uc.servingwebcontent.service.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    @Autowired
    private Converter converter;

    @PostMapping("/convert")
    public ResponseEntity<String> convert(@RequestBody Request request) {
        return converter.convert(request.getFrom(), request.getTo());
    }
}