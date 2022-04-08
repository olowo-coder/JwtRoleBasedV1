package com.example.jwtrolebased.service;

import com.example.jwtrolebased.dto.RegisterReq;
import com.example.jwtrolebased.dto.TestResponse;
import com.example.jwtrolebased.dto.UserResponse;
import com.example.jwtrolebased.entity.RegisterUser;
import com.example.jwtrolebased.entity.Role;
import com.example.jwtrolebased.entity.UserModel;
import com.example.jwtrolebased.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserModelService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserModelService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterUser loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Not Found: " + username));
        return new RegisterUser(userModel);
    }

    public UserResponse registerUser(RegisterReq request, Role role){
        UserModel userModel = new UserModel();
        userModel.setUsername(request.getUsername());
        userModel.setPassword(passwordEncoder.encode(request.getPassword()));
        userModel.setRole(role);
        userRepository.save(userModel);
        return UserResponse.builder().message("Registered").build();
    }

    public RegisterUser findById(Long id){
        return new RegisterUser(userRepository.findById(id).orElse(null));
    }

}
