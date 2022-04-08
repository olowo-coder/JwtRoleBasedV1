package com.example.jwtrolebased.controller;

import com.example.jwtrolebased.dto.RegisterReq;
import com.example.jwtrolebased.dto.TestResponse;
import com.example.jwtrolebased.dto.UserResponse;
import com.example.jwtrolebased.entity.Role;
import com.example.jwtrolebased.service.UserModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    private final UserModelService userModelService;

    @Autowired
    public UserController(UserModelService userModelService) {
        this.userModelService = userModelService;
    }

    @PostMapping("/register/user")
    public ResponseEntity<UserResponse> addUser(@RequestBody final RegisterReq request){
       return ResponseEntity.ok(userModelService.registerUser(request, Role.ROLE_USER));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<UserResponse> addAdmin(@RequestBody final RegisterReq request){
       return ResponseEntity.ok(userModelService.registerUser(request, Role.ROLE_ADMIN));
    }

    @GetMapping("/register/find/{id}")
    public ResponseEntity<?> byId(@PathVariable Long id) {
        return ResponseEntity.ok(userModelService.findById(id));
    }

    @GetMapping({"/test/admin"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/test/user"})
    @PreAuthorize("hasRole('ROLE_USER')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }
}
