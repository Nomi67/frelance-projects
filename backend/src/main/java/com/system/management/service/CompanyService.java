/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.service;

import com.system.management.configs.UserLogsUtils;
import com.system.management.enums.Status;
import com.system.management.model.Company;
import com.system.management.model.User;
import com.system.management.objects.NewCompany;
import com.system.management.objects.Response;
import com.system.management.repo.CompanyRepo;
import com.system.management.repo.UserRepo;
import java.util.Date;
import java.util.List;
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
public class CompanyService {

    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserLogsUtils logsUtils;

    public List<String> getAllCompaniesNames() {
        return companyRepo.findAllName();
    }

    public Page<Company> getAllCompanies(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return companyRepo.findAll(pageable);
    }

    public Company getCompanyByName(String name) {
        Optional<Company> company = companyRepo.findByName(name);
        if (company.isPresent()) {
            return company.get();
        } else {
            return null;
        }
    }

    public Response createCompany(NewCompany newCompany) {
        Optional<Company> getCompany = companyRepo.findByName(newCompany.getName());
        if (!getCompany.isPresent()) {
            Company company = new Company(newCompany.getName(), Status.ACTIVE, newCompany.getTelePhone(), newCompany.getBusinessRegNo(), newCompany.getDescription(), newCompany.getAddress(), newCompany.getCreatedBy());
            companyRepo.save(company);
            logsUtils.saveUserLog(newCompany.getCreatedBy(), "CREATE COMPANY : "+newCompany.getName());
            return new Response("0000", null, companyRepo.findAll());
        } else {
            logsUtils.saveUserLog(newCompany.getCreatedBy(), "CREATE COMPANY FAILED");
            return new Response("1001", null, null);
        }
    }

    public Response getComapniesByFilters(String name, String address, String regNo) {
        String cName = name.trim();
        String cAddress = address.trim();
        String cRegNo = regNo.trim();
        System.out.println("name:" + cName + " adress:" + cAddress + " regNO:" + cRegNo);
        List<Company> company = companyRepo.findAllByNameOrBusinessRegNoOrAddress(cName, cRegNo, cAddress);

        if (!company.isEmpty()) {
            return new Response("0000", null, company);
        } else {
            return new Response("1001", null, null);
        }
    }

    public Response updateCompanyData(Company company, String username) {
        Optional<Company> comp = companyRepo.findById(company.getId());
        if (comp.isPresent()) {
            comp.get().setName(company.getName());
            comp.get().setAddress(company.getAddress());
            comp.get().setBusinessRegNo(company.getBusinessRegNo());
            comp.get().setTelePhone(company.getTelePhone());
            comp.get().setDescription(company.getDescription());
            comp.get().setUpdated(new Date(System.currentTimeMillis()));
            comp.get().setUpdatedBy(username);
            companyRepo.save(comp.get());
            logsUtils.saveUserLog(username, "UPDATE COMPANY : "+comp.get().getName());
            return new Response("0000", null, companyRepo.findAll());
        } else {
            return new Response("1001", null, null);
        }
    }

    public Response changeCompanyStatus(Long id, String status, String username) {
        if (status.equals("INACTIVE")) {
            List<User> users = userRepo.findAllByCompany_Id(id);
            if (!users.isEmpty()) {
                for (int i = 0; i < users.size(); i++) {
                    users.get(i).setStatus(Status.INACTIVE);
                    users.get(i).setUpdated(new Date(System.currentTimeMillis()));
                    users.get(i).setUpdatedBy(username);
                    logsUtils.saveUserLog(username, "USER DISABLE : "+users.get(i).getUserName());
                }
                userRepo.saveAll(users);
            }
            Optional<Company> company = companyRepo.findById(id);
            company.get().setStatus(Status.INACTIVE);
            company.get().setUpdated(new Date(System.currentTimeMillis()));
            company.get().setUpdatedBy(username);
            companyRepo.save(company.get());
            logsUtils.saveUserLog(username, "COMPANY DISABLE");
            return new Response("0000", null, companyRepo.findAll());
        } else if (status.equals("ACTIVE")) {
            Optional<Company> company = companyRepo.findById(id);
            company.get().setStatus(Status.ACTIVE);
            companyRepo.save(company.get());
            logsUtils.saveUserLog(username, "COMPANY ENABLE");
            return new Response("0000", null, companyRepo.findAll());
        } else {
            return new Response("1001", null, null);
        }
    }
}
