/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.repo;

import com.system.management.enums.Status;
import com.system.management.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Nauman
 */
public interface UserRepo extends JpaRepository<User, Long>{
    List<User> findAllByCompany_Id(Long id);
    Optional<User> findByUserNameAndStatus(String username,Status status);
    Optional<User> findByUserName(String username);
    Optional<User> findFirstByCompany_IdAndRole_Id(Long cId,Long rId);
    Page<User> findAllByUserNameOrCompany_Id(String username,Long id,Pageable pageable);
    Page<User> findAllByStatus(Status status,Pageable pageable);
    Page<User> findAllByCompany_Id(Long id,Pageable pageable);
    
}
