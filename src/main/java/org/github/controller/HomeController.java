package org.github.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to GitHub Repository Activity Connector!!!");
        return "home"; // This refers to 'home.html' in the templates folder
    }
}
