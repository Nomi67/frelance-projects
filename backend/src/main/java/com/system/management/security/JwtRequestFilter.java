/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author Nauman
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter{
    
    @Autowired
	private UserDetailsProtocolService userDetailService;
	@Autowired
	private JwtUtils jwtUtils;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException
	{
		final String header=request.getHeader("authentication");
		String userName=null;
		String jwt=null;
		if(header!=null)
		if(header!=null && header.startsWith("nomi"))
		{
			jwt=header.substring(5);
			userName=jwtUtils.getUsernameFromToken(jwt);
		}
		if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails=userDetailService.loadUserByUsername(userName);
			if(jwtUtils.validateToken(jwt, userDetails))
			{
				UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(token);
				
			}
		}
		filterChain.doFilter(request, response);
		
	}
    
}
