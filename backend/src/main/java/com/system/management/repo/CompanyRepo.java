/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.repo;

import com.system.management.model.Company;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Nauman
 */
public interface CompanyRepo extends JpaRepository<Company, Long>{
    
    @Query(value = "SELECT c.name FROM Company AS c")
    List<String> findAllName();
    Optional<Company> findByName(String name);
    @Query(value = "SELECT c FROM Company c "
            +"WHERE (:name is NULL OR c.name=:name) "
            +"AND(:businessRegNo is NULL OR c.businessRegNo=:businessRegNo) "
            +"AND(:address is NULL OR c.address=:address) "
    )
    List<Company> findAllByNameOrBusinessRegNoOrAddress( @Param("name") String name,
            @Param("businessRegNo") String businessRegNo,
            @Param("address") String address);
    Optional<Company> findByNameAndUsers_Id(String username,Long id);
}
