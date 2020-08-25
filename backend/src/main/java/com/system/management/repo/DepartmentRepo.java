/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.repo;

import com.system.management.model.Department;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Nauman
 */
public interface DepartmentRepo extends JpaRepository<Department,Long>{
    @Transactional
    void deleteByLocation_Id(Long id);
    List<Department> findAllByLocation_id(Long id);
    
}
