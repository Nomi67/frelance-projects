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
public class NewLocation {
    private String street;
    private String city;
    private String country;
    private String postCode;
    private String companyName;

    public NewLocation() {
    }

    public NewLocation(String street, String city, String country, String postCode, String companyName) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.postCode = postCode;
        this.companyName = companyName;
    }

    
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
}
