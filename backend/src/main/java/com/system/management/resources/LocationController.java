/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.resources;

import com.system.management.model.Location;
import com.system.management.objects.NewLocation;
import com.system.management.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/location")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class LocationController {
    @Autowired
    private LocationService locationService;
    
    @GetMapping("/company-locations")
    public Page<Location> getLocationsOfCompany(@RequestParam("name") String name,@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize,@RequestParam("jwt") String token)
    {
        return locationService.getCompanyLocations(name, pageNo, pageSize,token);
    }
    @PutMapping("/company-locations")
    public void updateLocation(@RequestBody Location location,@RequestParam("username") String username)
    {
        locationService.updateLocation(location,username);
    }
    @PostMapping("/company-locations")
    public void addNewLocation(@RequestBody NewLocation location,@RequestParam("username") String username)
    {
        locationService.addNewLocation(location,username);
    }
    
    @DeleteMapping("/company-locations")
    public void deleteLocationById(@RequestParam("id") Long id,@RequestParam("username") String username)
    {
        locationService.deleteLocation(id,username);
    }
}
