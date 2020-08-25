/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.security;

import com.system.management.model.Permissions;
import com.system.management.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Nauman
 */
public class UserDetaileProtocol implements UserDetails{
    private static final long serialVersionUID = 1L;
    private User user;
    private List<GrantedAuthority> authorities;

    public UserDetaileProtocol() {
    }

    public UserDetaileProtocol(User user) {
        this.user = user;
        authorities=new ArrayList();
          this.user.getRole().getPermissions().forEach(p ->{
              GrantedAuthority auth=new SimpleGrantedAuthority(p.getName());
              authorities.add(auth);
          });

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    
}
