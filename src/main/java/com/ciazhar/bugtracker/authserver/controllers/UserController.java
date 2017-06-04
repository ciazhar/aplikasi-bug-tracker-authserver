package com.ciazhar.bugtracker.authserver.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ciazhar on 5/27/17.
 */

@RestController
@RequestMapping("api/user")
public class UserController {

    @RequestMapping("/current")
    public Authentication currentUser(Authentication authentication){
        return authentication;
    }
}
