/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.repo;

import com.system.management.model.Permissions;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Nauman
 */
public interface PermissionRepo extends JpaRepository<Permissions, Long>{
//    List<Permissions> findAllByRole_Id(Long id);
    Optional<Permissions> findByName(String name);
}
