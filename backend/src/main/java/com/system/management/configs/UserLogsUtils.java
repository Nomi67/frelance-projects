/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.configs;

import com.system.management.model.User;
import com.system.management.model.UserLogs;
import com.system.management.repo.UserLogsRepo;
import com.system.management.repo.UserRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nauman
 */
@Component
public class UserLogsUtils {
    @Autowired
    private UserLogsRepo logsRepo;
    @Autowired
    private UserRepo userRepo;
    
    public void saveUserLog(String username,String action)
    {
        Optional<User> user=userRepo.findByUserName(username);
        if(user.isPresent())
        {
            if(user.get().getRole().getName().equals("SUPER_ADMIN"))
            {
                UserLogs log=new UserLogs(username, "super admin", action);
                logsRepo.save(log);
            }
            else
            {
            UserLogs log=new UserLogs(username, user.get().getCompany().getName(), action);
            logsRepo.save(log);
            }
        }
    }
}
