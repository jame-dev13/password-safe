package com.jame.safepassword.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.Serializable;

@Controller
public class Index implements Serializable {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/tests")
    public String tests(){
        return "tests";
    }
}
