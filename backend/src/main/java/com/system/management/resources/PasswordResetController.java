/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.resources;

import com.system.management.model.PasswordReset;
import com.system.management.objects.Response;
import com.system.management.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/password")
public class PasswordResetController {
    
    @Autowired
    private PasswordResetService resetService;
    
    @GetMapping("/change")
    public Response sendMailOfPasswordReset(@RequestParam("resetId") Long resetId,@RequestParam("adminUsername") String adminName){
        return resetService.sendEmailForPasswordreset(resetId,adminName);
    }
    @PostMapping("/request")
    public Response submitPasswordResetRequest(@RequestParam("name") String name)
    {
//        System.out.println("Username is :"+name);
        return resetService.submitPasswordResetRequest(name);
    }
    @GetMapping("/get-all")
    public Page<PasswordReset> getAllResetrequets(@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize,@RequestParam("username") String username)
    {
        return resetService.getPasswordResetRequest(pageNo, pageSize,username);
    }
}
