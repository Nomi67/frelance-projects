/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.resources;

import com.system.management.model.UserLogs;
import com.system.management.service.UserLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nauman
 */
@RestController
@RequestMapping("/logs")
public class UserLogsController {
    @Autowired
    private UserLogsService logsService;
    @GetMapping
    public Page<UserLogs> getUserLogs(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize, @RequestParam("username") String username)
    {
        return logsService.getUserLogs(username, pageNo, pageSize);
    }
}
