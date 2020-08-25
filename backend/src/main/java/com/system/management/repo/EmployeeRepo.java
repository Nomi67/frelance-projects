/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.repo;

import com.system.management.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Nauman
 */
public interface EmployeeRepo extends JpaRepository<Employee, Long>{
    Page<Employee> findAllByCompany_id(Long id,Pageable pageable);
}
