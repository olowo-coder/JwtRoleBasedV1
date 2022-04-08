package com.example.jwtrolebased.service;

import com.example.jwtrolebased.dto.LoginReq;
import com.example.jwtrolebased.dto.UserResponse;
import com.example.jwtrolebased.entity.RegisterUser;
import com.example.jwtrolebased.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final AuthenticationManager authenticationManager;

    private final UserModelService myUserDetailsService;

    private final JwtUtil jwtUtil;

    @Autowired
    public LoginService(AuthenticationManager authenticationManager,
                        UserModelService myUserDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public UserResponse createJwtToken(LoginReq loginReq) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(),
                    loginReq.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            final RegisterUser userDetails = myUserDetailsService.loadUserByUsername(loginReq.getUsername());
//            final String jwt = jwtUtil.generateToken(userDetails);
            final String jwt = jwtUtil.generateToken(authentication);

            return UserResponse.builder().user(userDetails).message("logged in").token(jwt).build();
        }
        catch (BadCredentialsException e){
            throw new UsernameNotFoundException("INVALID_CREDENTIALS");
        }
    }
}
