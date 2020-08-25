/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.resources;

import com.system.management.model.Company;
import com.system.management.objects.NewCompany;
import com.system.management.objects.Response;
import com.system.management.service.CompanyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/company")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    
    @GetMapping("/names")
    public List<String> getAllCompaniesNames(){
        return companyService.getAllCompaniesNames();
    }
    @GetMapping("/all")
    public Page<Company> getAllCompanies(@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize){
        return companyService.getAllCompanies(pageNo,pageSize);
    }
    @PostMapping("/create")
    public Response createNewCompany(@RequestBody NewCompany company )
    {
        return companyService.createCompany(company);
    }
    @GetMapping("/company-filter")
    public Response getCompanyByFilter(@RequestParam("name") String name,@RequestParam("regNo") String regNo,@RequestParam("location") String location)
    {
        return companyService.getComapniesByFilters(name, location,regNo);
    }
    @PutMapping("/edit")
    public Response updateCompanyData(@RequestBody Company company, @RequestParam("username") String username)
    {
        return companyService.updateCompanyData(company,username);
    }
    
    @GetMapping("/change-status")
    public Response changeCompanyStatus(@RequestParam("id") Long id,@RequestParam("status") String status,@RequestParam("username") String username)
    {
        return companyService.changeCompanyStatus(id, status,username);
    }
}
