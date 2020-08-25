/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.resources;

import com.system.management.model.Department;
import com.system.management.objects.NewDepartment;
import com.system.management.service.DepartmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/department")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    
    @GetMapping("/by-location")
    public List<Department> getAllDepartmentsByLocation(@RequestParam("id") Long id)
    {
        return departmentService.getAllDepartmentsByLocation(id);
    }
    @PutMapping("/by-location")
    public void updateDepartment(@RequestBody Department department,@RequestParam("username") String username)
    {
        departmentService.updateDepartment(department,username);
    }
    @PostMapping("/by-location")
    public void addNewDepartment(@RequestBody NewDepartment dep,@RequestParam("username") String username)
    {
        departmentService.addNewDepartment(dep,username);
    }
    
}
