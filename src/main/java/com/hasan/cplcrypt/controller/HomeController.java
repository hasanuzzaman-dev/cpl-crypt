package com.hasan.cplcrypt.controller;

import com.hasan.cplcrypt.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    private String home(Model model){
        model.addAttribute("title", "SignIn - cpl crypt");
        model.addAttribute("user", new User());
        return "signIn";
    }

    @RequestMapping("/about")
    private String about(Model model){
        model.addAttribute("title", "About - cpl crypt");
        return "about";
    }

    @RequestMapping("/sign-up")
    private String signUp(Model model){
        model.addAttribute("title", "SignUp - cpl crypt");
        model.addAttribute("user", new User());
        return "sign_up";
    }

    @RequestMapping("/signIn")
    private String signIp(Model model){
        model.addAttribute("title", "SignIn - cpl crypt");
        model.addAttribute("user", new User());
        return "signIn";
    }


}
