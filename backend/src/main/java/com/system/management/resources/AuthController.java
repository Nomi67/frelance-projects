/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.resources;

import com.system.management.model.User;
import com.system.management.objects.Response;
import com.system.management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nauman
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @GetMapping("/login")
    public Response authenticateUser(@RequestParam("username") String username,@RequestParam("password") String password) throws Exception
    {
        return authService.authenticateUser(username, password);
    }
    @GetMapping("/user")
    public User getUser(@RequestParam("name") String name)
    {
        return authService.getUser(name);
    }
    @GetMapping("/permissions")
    public Response getUserPermissions(@RequestParam("jwt") String jwt)
    {
        return authService.getUserPermissions(jwt);
    }
}
