package com.pro0inter.chatserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConnectivity {
    @GetMapping("/test")
    public boolean test(){
        return true;
    }
}
