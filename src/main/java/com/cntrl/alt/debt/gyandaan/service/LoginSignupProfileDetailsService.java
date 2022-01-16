package com.cntrl.alt.debt.gyandaan.service;

import org.springframework.web.bind.annotation.RequestParam;

public interface LoginSignupProfileDetailsService {
    String getUsernameLogin(String email,String password, String type);
    
}
