/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.resources;

import com.system.management.model.Role;
import com.system.management.objects.Response;
import com.system.management.service.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nauman
 */
@RestController
@RequestMapping("/role")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping
    public List<String> getAllRolesName()
    {
        return roleService.getAllRoles();
    }
    @PostMapping
    public Response createNewRole(@RequestBody List<String> perms,@RequestParam("name") String name,@RequestParam("username") String username)
    {
        return roleService.createNewRole(name, perms,username);
    }
}
