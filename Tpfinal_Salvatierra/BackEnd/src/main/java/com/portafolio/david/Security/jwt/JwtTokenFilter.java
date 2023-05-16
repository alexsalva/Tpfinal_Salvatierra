/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portafolio.david.Security.jwt;


import com.portafolio.david.Security.Service.UserDetailsImpl;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenFilter extends OncePerRequestFilter {
   private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
   @Autowired
   JwtProvider jwtProvider;
   @Autowired
   UserDetailsImpl userDetailsServiceImpl;

   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      try {
         String token = this.getToken(request);
         if (token != null && this.jwtProvider.validateToken(token)) {
            String nombreUsuario = this.jwtProvider.getNombreUSuarioFromToken(token);
            UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(nombreUsuario);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, (Object)null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
         }
      } catch (Exception var8) {
         logger.error("Fallo el metodo doFilterInternal");
      }

      filterChain.doFilter(request, response);
   }

   private String getToken(HttpServletRequest request) {
      String header = request.getHeader("Authorization");
      return header != null && header.startsWith("Bearer") ? header.replace("Bearer", "") : null;
   }
}