/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.service;

import com.system.management.configs.UserLogsUtils;
import com.system.management.model.Company;
import com.system.management.model.Employee;
import com.system.management.model.User;
import com.system.management.objects.NewEmployee;
import com.system.management.repo.CompanyRepo;
import com.system.management.repo.EmployeeRepo;
import com.system.management.repo.UserRepo;
import com.system.management.security.JwtUtils;
import java.util.Date;
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
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserLogsUtils logsUtils;

    
    public Page<Employee> getAllEmployeesInCompany(String cmpName,int pageNo,int pageSize,String token)
    {
//        String jwt=token.substring(5);
        String username=jwtUtils.getUsernameFromToken(token);
        Optional<User> user=userRepo.findByUserName(username);
        Optional<Company> company=companyRepo.findByNameAndUsers_Id(cmpName,user.get().getId());
        if(company.isPresent())
        {
        Pageable pageable=PageRequest.of(pageNo, pageSize);
        return employeeRepo.findAllByCompany_id(company.get().getId(), pageable);
        }
        else
            return null;
    }
    public void updateEmployee(Employee employee,String username)
    {
        Optional<Employee> emp=employeeRepo.findById(employee.getId());
        if(emp.isPresent())
        {
            emp.get().setFirstName(employee.getFirstName());
            emp.get().setLastName(employee.getLastName());
            emp.get().setRegNo(employee.getRegNo());
            emp.get().setPhone(employee.getPhone());
            emp.get().setEmail(employee.getEmail());
            emp.get().setDesignation(employee.getDesignation());
            emp.get().setUpdated(new Date(System.currentTimeMillis()));
            emp.get().setUpdatedBy(username);
            logsUtils.saveUserLog(username, "UPDATE EMPLOYEE : "+emp.get().getFirstName()+" "+emp.get().getLastName());
            employeeRepo.save(emp.get());
        }
    }
    
    public void createNewEmployee(NewEmployee newEmployee,String username)
    {
        Optional<Company> company=companyRepo.findByName(newEmployee.getCompanyName());
        if(company.isPresent())
        {
            Employee employee=new Employee(newEmployee.getFirstName(), newEmployee.getLastName(), newEmployee.getEmail(), newEmployee.getPhone(), newEmployee.getDesignation(), newEmployee.getRegNo(), newEmployee.getCreatedBy());
            employee.setCompany(company.get());
            employeeRepo.save(employee);
            logsUtils.saveUserLog(username, "CREATE EMPLOYEE : "+employee.getFirstName()+" "+employee.getLastName());
        }
        
    }
    
}
