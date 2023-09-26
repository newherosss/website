package com.ohgiraffers.rest.testcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HiController {

    @GetMapping("/upload")
    public String testUploadForm() {

        return "download" ;
    }
}
