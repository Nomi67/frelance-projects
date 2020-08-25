/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.repo;

import com.system.management.model.Location;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 *
 * @author Nauman
 */
public interface LocationRepo extends JpaRepository<Location, Long>{
    @Transactional
    Page<Location> findAllByCompany_Id(Long id,Pageable pageable);
}
