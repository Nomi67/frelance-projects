/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.repo;

import com.system.management.model.PasswordReset;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Nauman
 */
public interface PasswordResetRepo extends JpaRepository<PasswordReset, Long>{
    Optional<PasswordReset> findByUsername(String username);
    Page<PasswordReset> findAllByCompany(String company,Pageable pageable);
}
