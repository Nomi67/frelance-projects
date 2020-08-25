/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.service;

import com.system.management.configs.UserLogsUtils;
import com.system.management.enums.Status;
import com.system.management.model.Department;
import com.system.management.model.Location;
import com.system.management.objects.NewDepartment;
import com.system.management.repo.DepartmentRepo;
import com.system.management.repo.LocationRepo;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nauman
 */
@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private LocationRepo locationRepo;
    @Autowired
    private UserLogsUtils logsUtils;

    
    public List<Department> getAllDepartmentsByLocation(Long id)
    {
        return departmentRepo.findAllByLocation_id(id);
    }
    
    public void addNewDepartment(NewDepartment dep,String username)
    {
        Optional<Location> location=locationRepo.findById(dep.getLocationId());
        if(location.isPresent())
        {
        Department department=new Department(dep.getName(), Status.ACTIVE, username);
        department.setLocation(location.get());
        departmentRepo.save(department);
        logsUtils.saveUserLog(username, "CREATE DEPARTMENT : "+department.getName());
        }
    }
    public void updateDepartment(Department department,String username)
    {
        Optional<Department> dep=departmentRepo.findById(department.getId());
        if(dep.isPresent())
        {
            dep.get().setName(department.getName());
            dep.get().setUpdated(new Date(System.currentTimeMillis()));
            dep.get().setUpdatedBy(username);
            departmentRepo.save(dep.get());
            logsUtils.saveUserLog(username, "UPDATE DEPARTMENT : "+dep.get().getName());
        }
        
    }
}
