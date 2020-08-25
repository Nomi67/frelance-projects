/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.service;

import com.system.management.configs.UserLogsUtils;
import com.system.management.enums.Status;
import com.system.management.model.Company;
import com.system.management.model.Location;
import com.system.management.model.User;
import com.system.management.objects.NewLocation;
import com.system.management.repo.CompanyRepo;
import com.system.management.repo.DepartmentRepo;
import com.system.management.repo.LocationRepo;
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
public class LocationService {
    @Autowired
    private LocationRepo locationRepo;
    @Autowired 
    private CompanyRepo companyRepo;
    @Autowired 
    private DepartmentRepo departmentRepo;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserLogsUtils logsUtils;
    
    public Page<Location> getCompanyLocations(String companyName,int pageNo,int pageSize,String token)
    {
        String username=jwtUtils.getUsernameFromToken(token);
        Optional<User> user=userRepo.findByUserName(username);
        Optional<Company> company=companyRepo.findByNameAndUsers_Id(companyName,user.get().getId());
        if(company.isPresent()){
            Pageable pageable=PageRequest.of(pageNo, pageSize);
            Page<Location> location= locationRepo.findAllByCompany_Id(company.get().getId(), pageable);
            return location;
        }
        else
            return null;
    }
    
    public void updateLocation(Location location,String username)
    {
        Optional<Location> locationNew=locationRepo.findById(location.getId());
        if(locationNew.isPresent())
        {
            locationNew.get().setCity(location.getCity());
            locationNew.get().setCountry(location.getCountry());
            locationNew.get().setStreet(location.getStreet());
            locationNew.get().setPostCode(location.getPostCode());
            locationNew.get().setUpdated(new Date(System.currentTimeMillis()));
            locationNew.get().setUpdatedBy(username);
            locationRepo.save(locationNew.get());
            logsUtils.saveUserLog(username, "UPDATE LOCATION "+locationNew.get().getCity());
        }
        else
            System.out.println("Update Failed");
    }
    public void addNewLocation(NewLocation location,String username)
    {
        Optional<Company> company=companyRepo.findByName(location.getCompanyName());
        if(company.isPresent())
        {
            
        
        Location newLocation=new Location(location.getStreet(), location.getCity(), location.getCountry(), location.getPostCode(), Status.ACTIVE, username);
        newLocation.setCompany(company.get());
        locationRepo.save(newLocation);
        logsUtils.saveUserLog(username, "CREATE LOCATION "+newLocation.getCity());
        }
    }
    
    public void deleteLocation(Long id,String username)
    {
        departmentRepo.deleteByLocation_Id(id);
        Optional<Location> location=locationRepo.findById(id);
        
        if(location.isPresent())
                logsUtils.saveUserLog(username, "DELETE LOCATION AND DEPARTMENTS UNDER IT"+location.get().getCity());
        locationRepo.deleteById(id);

    }
}
