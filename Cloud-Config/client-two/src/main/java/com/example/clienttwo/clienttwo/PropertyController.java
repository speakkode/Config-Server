package com.example.clienttwo.clienttwo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class PropertyController {

    @Value("${mongodb.host:Hello world - Config Server is not working..pelase check}")
    private String port;

    @RequestMapping("/port")
    String getMsg() {
        return this.port;
    }

}
