package com.hasan.cplcrypt.controller;

import com.hasan.cplcrypt.models.User;
import com.hasan.cplcrypt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String dashBoard(Model model, Principal principal){
        String userName = principal.getName();
        System.out.println("USERNAME: "+userName);

        // get the username using username
        User user = userService.getUserByUserName(userName);
        System.out.println("USER: "+user.toString());
        model.addAttribute("user", user);
        model.addAttribute("title", "home");

        return "normal/user_dashboard";
    }
}
