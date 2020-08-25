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
public class NewCompany {
    private String name;
    private String telePhone;
    private String businessRegNo;
    private String description;
    private String address;
    private String createdBy;

    public NewCompany() {
    }

    public NewCompany(String name, String telePhone, String businessRegNo, String description, String address, String createdBy) {
        this.name = name;
        this.telePhone = telePhone;
        this.businessRegNo = businessRegNo;
        this.description = description;
        this.address = address;
        this.createdBy = createdBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getBusinessRegNo() {
        return businessRegNo;
    }

    public void setBusinessRegNo(String businessRegNo) {
        this.businessRegNo = businessRegNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
}
