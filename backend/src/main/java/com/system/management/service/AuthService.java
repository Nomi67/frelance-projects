/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.service;

import com.system.management.configs.UserLogsUtils;
import com.system.management.enums.Status;
import com.system.management.model.User;
import com.system.management.objects.AuthResponse;
import com.system.management.objects.Response;
import com.system.management.repo.UserRepo;
import com.system.management.security.JwtUtils;
import com.system.management.security.UserDetailsProtocolService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nauman
 */
@Service
public class AuthService {

//    @Autowired
//    private UserRepo userRepo;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private UserDetailsProtocolService userDetailService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserLogsUtils logsUtils;

    public Response authenticateUser(String username, String password) throws Exception {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            System.out.println("auth failed");
            return new Response("1001", "Invalid username or password", null);
        }
        try {
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            User user = userDetailService.getUserData(username);
            String jwt = jwtUtils.generateToken(userDetails, user);
            AuthResponse authResponse = new AuthResponse(jwt, user.getRole().getName(), user);
            logsUtils.saveUserLog(username, "LOGGED IN USER:"+username);
            return new Response("0000", null, authResponse);
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            logsUtils.saveUserLog(username, "LOG IN FAILED");
            return new Response("1001", null, null);
        }
    }

    public User getUser(String name) {
//        return userRepo.findByUserName(name).get();
        return userRepo.findByUserNameAndStatus(name, Status.ACTIVE).get();
    }

    public Response getUserPermissions(String jwt) {
        String username = jwtUtils.getUsernameFromToken(jwt);
//        Long userId=Long.parseLong(id);
        Optional<User> user = userRepo.findByUserName(username);
        if (user.isPresent()) {
            return new Response("0000", null, user.get().getRole().getPermissions());
        } else {
            return new Response("1001", null, null);
        }
    }
}
