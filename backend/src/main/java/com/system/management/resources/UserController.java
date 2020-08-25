/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.resources;

import com.system.management.model.User;
import com.system.management.objects.NewUser;
import com.system.management.objects.Response;
import com.system.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nauman
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    
    @PutMapping
    public Response updateUserData(@RequestBody User updateUser)
    {
        return userService.updateUser(updateUser);
    }
    @GetMapping
    public Page<User> getAllUsers(@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize,@RequestParam("username") String username)
    {
        return userService.getAllUsers(pageNo, pageSize,username);
    }
    @PutMapping("/change-role")
    public Response changeUserRole(@RequestParam("id") Long id,@RequestParam("role") String role,@RequestParam("updatedBy") String updatedBy)
    {
        return userService.changeUserRole(role, updatedBy, id);
    }
    @DeleteMapping
    public Response deleteUser(@RequestParam("id") Long id,@RequestParam("username") String username)
    {
        return userService.deleteUser(id,username);
    }
    @GetMapping("/filter")
    public Page<User> getUserByFilter(@RequestParam("username") String username,
            @RequestParam("cName") String cName,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("pageSize") int pageSize
            )
    {
        return userService.getUserByFilter(username, cName, pageNo, pageSize);
    }
    @PostMapping("/add")
    public Response createNewUser(@RequestBody NewUser newUser,@RequestParam("username") String username)
    {
        return userService.addNewUser(newUser,username);
    }
    @GetMapping("/disable")
    public Response disableUser(@RequestParam("id") Long id,@RequestParam("username") String username)
    {
        return userService.disableUser(id,username);
    }
}
