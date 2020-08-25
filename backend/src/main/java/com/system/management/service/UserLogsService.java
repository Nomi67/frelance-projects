/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.service;

import com.system.management.model.User;
import com.system.management.model.UserLogs;
import com.system.management.repo.UserLogsRepo;
import com.system.management.repo.UserRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nauman
 */
@Service
public class UserLogsService {
    @Autowired
    private UserLogsRepo logsRepo;
    @Autowired
    private UserRepo userRepo;
    public Page<UserLogs> getUserLogs(String username, int pageNo, int pagesize)
    {
        Pageable pageable=PageRequest.of(pageNo, pagesize);
        Optional<User> user=userRepo.findByUserName(username);
        if(user.get().getRole().getName().equals("SUPER_ADMIN"))
        {
            return logsRepo.findAll(pageable);
        }
        else 
        {
            return logsRepo.findAllByCompany(user.get().getCompany().getName(), pageable);
        }
    }
}
