package com.notes_api_service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notes_api_service.handler.GenericResponse;
import com.notes_api_service.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try{
           String authorizationHeader = request.getHeader("Authorization");
           String token = null;
           String username = null;
           if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
               token = authorizationHeader.substring(7);
               username = jwtService.extractUserNameFromJwtToken(token);
               if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                   UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                   Boolean validateToken = jwtService.validateJwtToken(token, userDetails);
                   if (validateToken) {
                       UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                               null, userDetails.getAuthorities());
                       authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                       SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                   }
               }
           }
       }catch (Exception e){
           //e.printStackTrace();
           generateResponeError(response,e);
           return;
       }

        filterChain.doFilter(request, response);
    }

    private void generateResponeError(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Object error = GenericResponse.builder()
                .status("Failed")
                .message(e.getMessage())
                .responseStatus(HttpStatus.UNAUTHORIZED)
                .build().createResponseEntity().getBody();
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper()
                .writeValueAsString(error));
    }

}
