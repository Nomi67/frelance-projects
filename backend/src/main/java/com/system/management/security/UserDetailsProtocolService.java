/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.security;

import com.system.management.enums.Status;
import com.system.management.model.User;
import com.system.management.repo.UserRepo;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nauman
 */
@Service
public class UserDetailsProtocolService implements UserDetailsService{
    @Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user=userRepo.findByUserNameAndStatus(username, Status.ACTIVE);
		user.orElseThrow(()-> new UsernameNotFoundException("Not Found : "+username));
                user.get().setLastLogin(new Date(System.currentTimeMillis()));
                userRepo.save(user.get());
		return new UserDetaileProtocol(user.get());
	}
	
	public User getUserData(String userName)
	{
		return userRepo.findByUserName(userName).get();
	}
}
