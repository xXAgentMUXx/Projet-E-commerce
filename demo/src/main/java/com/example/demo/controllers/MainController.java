package com.example.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Power and create the road for the website of the API
@Controller
public class MainController {
    @GetMapping("/")
    public String home() {
        return "Ecommerce.html";  
    }
}