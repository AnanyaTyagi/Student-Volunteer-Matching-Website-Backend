package com.cntrl.alt.debt.gyandaan.controller;

import com.cntrl.alt.debt.gyandaan.service.LoginSignupProfileDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginSignupController {

    @Autowired
    LoginSignupProfileDetailsService loginSignupProfileDetailsService;

    @PostMapping("/login")
    public String loginUser(@RequestParam("email")final  String email,
                            @RequestParam("password") final String password,
                            @RequestParam("type") final String type) {

       return loginSignupProfileDetailsService.getUsernameLogin(email, password, type);
    }

    @PostMapping("/signup")
   public String registerUser(@RequestParam("email")final  String email,
                              @RequestParam("password") final String password,
                              @RequestParam("type") final String type){
        return null;
    }
}

