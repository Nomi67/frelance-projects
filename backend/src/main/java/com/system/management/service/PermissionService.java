/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.service;

import com.system.management.model.Role;
import com.system.management.model.User;
import com.system.management.objects.Response;
import com.system.management.repo.RoleRepo;
import com.system.management.repo.UserRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nauman
 */
@Service
public class PermissionService {
    
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    public Response getUserPermissions(String name)
    {
        Optional<User> user=userRepo.findByUserName(name);
        if(user.isPresent())
        {
            return new Response("0000", null, user.get().getRole().getPermissions());
        }
        else
            return new Response("1001", null, null);
    }
    
    public Response getPermissionsByCompanyName(String name)
    {
       Optional<Role> role=roleRepo.findByName(name);
       if(role.isPresent())
       {
           return new Response("0000", "found roles", role.get().getPermissions());
       }
       return new Response("1001", "role not found", null);
    }
}
