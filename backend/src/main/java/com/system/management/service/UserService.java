/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.service;

import com.system.management.configs.UserLogsUtils;
import com.system.management.enums.Status;
import com.system.management.model.Company;
import com.system.management.model.Role;
import com.system.management.model.User;
import com.system.management.objects.NewUser;
import com.system.management.objects.Response;
import com.system.management.repo.CompanyRepo;
import com.system.management.repo.RoleRepo;
import com.system.management.repo.UserRepo;
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
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private UserLogsUtils logsUtils;

    public Response updateUser(User updateUser) {
        System.out.println("username of being updated is:"+updateUser.getUserName());
        Optional<User> user = userRepo.findByUserName(updateUser.getUserName());
        if (user.isPresent()) {
            user.get().setFirstName(updateUser.getFirstName());
            user.get().setLastName(updateUser.getLastName());
            user.get().setEmail(updateUser.getEmail());
            user.get().setPassword(updateUser.getPassword());
            user.get().setPhone(updateUser.getPhone());
            user.get().setUpdated(new Date(System.currentTimeMillis()));
            user.get().setUpdatedBy(updateUser.getUpdatedBy());
            userRepo.save(user.get());
            logsUtils.saveUserLog(updateUser.getUpdatedBy(), "UPDATE USER : "+updateUser.getUserName());
            return new Response("0000", null, null);
        } else {
            return new Response("1001", null, null);
        }
    }

    public Page<User> getAllUsers(int pageNo, int pageSize, String username) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Optional<User> user = userRepo.findByUserName(username);
        if (user.isPresent()) {
            if (user.get().getRole().getName().equals("SUPER_ADMIN")) {
                return userRepo.findAllByStatus(Status.ACTIVE, pageable);
            } else {
                Long companyName = user.get().getCompany().getId();
                return userRepo.findAllByCompany_Id(companyName, pageable);
            }
        }
        return null;
    }

    public Response changeUserRole(String roleName, String updatedBy, Long userId) {

        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()) {
            Optional<Role> role = roleRepo.findByName(roleName);
            if (role.isPresent()) {
                user.get().setRole(role.get());
                user.get().setUpdated(new Date(System.currentTimeMillis()));
                user.get().setUpdatedBy(updatedBy);
                userRepo.save(user.get());
                logsUtils.saveUserLog(updatedBy, "CHANGE USER :"+user.get().getUserName()+" ROLE TO : "+roleName);
                return new Response("0000", null, null);
            }
            return new Response("1001", "role not found", null);
        }
        return new Response("1001", "user not found", null);
    }

    public Response deleteUser(Long id,String username) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            logsUtils.saveUserLog(username, "DELETE USER : "+user.get().getUserName());
            userRepo.delete(user.get());
            
            return new Response("0000", "user deleted", null);
        } else {
            return new Response("1001", "user not found", null);
        }
    }

    public Page<User> getUserByFilter(String username, String companyName, int pageNo, int pageSize) {
        Optional<Company> company = companyRepo.findByName(companyName);
        if (company.isPresent()) {

            Pageable pageable = PageRequest.of(pageNo, pageSize);

            return userRepo.findAllByUserNameOrCompany_Id(username, company.get().getId(), pageable);
        } else {
            return null;
        }
    }

    public Response addNewUser(NewUser newUser, String username) {

        if (userRepo.findByUserName(newUser.getUsername()).isPresent()) {
            return new Response("1001", "user already exists", null);
        }
        Optional<User> senderUser = userRepo.findByUserName(username);
        if (senderUser.get().getCompany() != null) {
            if(senderUser.get().getCompany().getName().equals(newUser.getCompany()))
            {
            Optional<Company> company = companyRepo.findByName(newUser.getCompany());
            if (company.isPresent()) {

                Optional<Role> role = roleRepo.findByName(newUser.getRole());
                User user = new User(newUser.getUsername(), newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getPassword(), newUser.getPhone(), Status.ACTIVE, null, new Date(System.currentTimeMillis()), newUser.getCreatedBy(), null, null);
                user.setRole(role.get());
                user.setCompany(company.get());
                userRepo.save(user);
                logsUtils.saveUserLog(username, "CREATE USER : "+user.getUserName());
                return new Response("0000", "success", null);
            }
            }
            return new Response("1002", "company not found", null);
            
        }
        else if(senderUser.get().getRole().getName().equals("SUPER_ADMIN"))
        {
            Optional<Company> company = companyRepo.findByName(newUser.getCompany());
            if (company.isPresent()) {

                Optional<Role> role = roleRepo.findByName(newUser.getRole());
                User user = new User(newUser.getUsername(), newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getPassword(), newUser.getPhone(), Status.ACTIVE, null, new Date(System.currentTimeMillis()), newUser.getCreatedBy(), null, null);
                user.setRole(role.get());
                user.setCompany(company.get());
                userRepo.save(user);
                logsUtils.saveUserLog(username, "CREATE USER : "+user.getUserName());
                return new Response("0000", "success", null);
            }
            return new Response("1002", "company not found", null);
        }
        return new Response("1003", "Can't Access Other company", null);

    }

    public Response disableUser(Long id,String username) {
        System.out.println("id:" + id);
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            user.get().setStatus(Status.INACTIVE);
            userRepo.save(user.get());
            logsUtils.saveUserLog(username, "DISABLE USER : "+user.get().getUserName());
            return new Response("0000", null, null);
        }
        return new Response("1001", "user not found", null);
    }
}
