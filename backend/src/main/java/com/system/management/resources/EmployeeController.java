/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.resources;

import com.system.management.model.Employee;
import com.system.management.objects.NewEmployee;
import com.system.management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nauman
 */
@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping("/by-company")
    public Page<Employee> getAllEmployeesInCompany(@RequestParam("company") String cmpName,@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize,@RequestParam("jwt") String token)
    {
        return employeeService.getAllEmployeesInCompany(cmpName, pageNo, pageSize,token);
    }
    
    @PutMapping("/by-company")
    public void updateEmployee(@RequestBody Employee employee,@RequestParam("username") String username)
    {
        employeeService.updateEmployee(employee,username);
    }
    @PostMapping("/by-company")
    public void createNewEmployee(@RequestBody NewEmployee employee,@RequestParam("username") String username)
    {
        employeeService.createNewEmployee(employee,username);
    }
}
