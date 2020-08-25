/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.service;

import com.system.management.configs.UserLogsUtils;
import com.system.management.model.Permissions;
import com.system.management.model.Role;
import com.system.management.objects.Response;
import com.system.management.repo.PermissionRepo;
import com.system.management.repo.RoleRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nauman
 */
@Service
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PermissionRepo permissionRepo;
    @Autowired
    private UserLogsUtils logsUtils;
    public List<String> getAllRoles()
    {
        List<String> roles=new ArrayList<>();
        
        roleRepo.findAll().stream().forEach(p -> {
            roles.add(p.getName());
        });
        return roles;
    }
    public Response createNewRole(String roleName,List<String> perms,String username)
    {
        String role=roleName.toUpperCase();
        if(roleRepo.findByName(role).isPresent())
        {
            return new Response("1001", "role exists already", null);
        }
        Role role1=new Role(role, username);
        List<Permissions> permissions=new ArrayList<>();
        Optional<Permissions> permItem=null;
        for(String name:perms)
        {
            permItem=permissionRepo.findByName(name);
            if(permItem.isPresent())
            {
                permissions.add(permItem.get());
            }
        }
        role1.setPermissions(permissions);
        roleRepo.save(role1);
        logsUtils.saveUserLog(username, "CREATED ROLE WITH NAME : "+role1.getName());
        return new Response("0000", "created role", getAllRoles());
    }
}
