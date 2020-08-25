/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.objects;

import com.system.management.model.User;

/**
 *
 * @author Nauman
 */
public class AuthResponse {
    private final String jwt;
	private final String role;
        private final User  user;

    public AuthResponse(String jwt, String role, User user) {
        this.jwt = jwt;
        this.role = role;
        this.user = user;
    }
	

    
	public String getJwt() {
		return jwt;
	}
	public String getRole() {
		return role;
	}
	

    public User getUser() {
        return user;
    }
        
}
