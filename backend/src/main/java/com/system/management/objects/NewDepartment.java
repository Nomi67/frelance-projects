/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.objects;

/**
 *
 * @author Nauman
 */
public class NewDepartment {
    private Long locationId;
    private String name;

    public NewDepartment() {
    }

    public NewDepartment(Long locationId, String name) {
        this.locationId = locationId;
        this.name = name;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
