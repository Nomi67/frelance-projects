/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.configs;

import com.system.management.enums.Status;
import com.system.management.model.Permissions;
import com.system.management.model.Role;
import com.system.management.model.User;
import com.system.management.repo.PermissionRepo;
import com.system.management.repo.RoleRepo;
import com.system.management.repo.UserRepo;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nauman
 */
@Component
public class PostConstructData {
    
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PermissionRepo permissionRepo;
    @Autowired
    private UserRepo userRepo;
    @PostConstruct
    public void addRoleAndPermissionsInDB(){
        roleRepo.deleteAll();
        permissionRepo.deleteAll();
        List<Role> roles=new ArrayList();
        List<Permissions> permission=new ArrayList<>();
        Role admin=new Role("ADMIN","system");
        Role manager=new Role("MANAGER","system");
        Role superAmdin=new Role("SUPER_ADMIN","system");
            Permissions permission1=new Permissions("ACCESS_USER_MANAGE");
            Permissions permission2=new Permissions("ACCESS_ROLE_MANAGEMENT");
            Permissions permission3=new Permissions("ACCESS_PERMISSION_ASSIGNING");
            Permissions permission4=new Permissions("ACCESS_CREATE_COMPANY");
            Permissions permission5=new Permissions("ACCESS_USER_LOGS");
            Permissions permission6=new Permissions("ACCESS_LOCATION");
            Permissions permission7=new Permissions("ACCESS_EMPLOYEE");
            admin.getPermissions().add(permission1);
            admin.getPermissions().add(permission2);
            admin.getPermissions().add(permission3);
            admin.getPermissions().add(permission5);
            admin.getPermissions().add(permission6);
            admin.getPermissions().add(permission7);
            manager.getPermissions().add(permission6);
            manager.getPermissions().add(permission7);
            superAmdin.getPermissions().add(permission1);
            superAmdin.getPermissions().add(permission2);
            superAmdin.getPermissions().add(permission3);
            superAmdin.getPermissions().add(permission4);
            superAmdin.getPermissions().add(permission5);
            superAmdin.getPermissions().add(permission6);
            superAmdin.getPermissions().add(permission7);
            permission.add(permission1);
            permission.add(permission2);
            permission.add(permission3);
            permission.add(permission4);
            permission.add(permission5);
            permission.add(permission6);
            permission.add(permission7);
            roles.add(admin);
            roles.add(manager);
            roles.add(superAmdin);
            roleRepo.saveAll(roles);
            permissionRepo.saveAll(permission);
            User user=new User("superAdmin", "root", "admin", null, "admin123", null, Status.ACTIVE, null, new Date(System.currentTimeMillis()), "System", null, null);
            user.setRole(superAmdin);
            userRepo.save(user);
            
    }
}
