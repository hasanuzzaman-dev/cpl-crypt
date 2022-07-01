package com.hasan.cplcrypt.controller;

import com.hasan.cplcrypt.helper.MyMessage;

import com.hasan.cplcrypt.models.Role;
import com.hasan.cplcrypt.models.User;
import com.hasan.cplcrypt.services.RoleService;
import com.hasan.cplcrypt.services.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class SignUpController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    // Handler for registering user
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult bindingResult,
                               @RequestParam(value = "agreement", defaultValue = "false") Boolean agreement,
                               Model model, HttpSession session){

        try {

            if (!agreement) {
                System.out.println("You have not agreed the terms & conditions");
                throw new Exception("You have not agreed the terms & conditions");
            }
            if (bindingResult.hasErrors()){
                model.addAttribute("user", user);
                return "sign_up";
            }
            Role role = roleService.findById(2);
            user.getRoles().add(role);

            user.setEnabled(true);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setImageUrl("default.png");
            System.out.println("USER: " + user.toString());
            System.out.println("AGREEMENT: " + agreement);

            User savedUser = userService.save(user);
            model.addAttribute("user", savedUser);

            session.setAttribute("message",new MyMessage("Successfully Registered!! ", "alert-success"));

            return "sign_in";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute(
                    "message",
                    new MyMessage("Something went wrong !! " + e.getMessage(), "alert-danger")
            );
            return "sign_up";
        }

    }
}
