package com.example.jwtrolebased.controller;

import com.example.jwtrolebased.dto.LoginReq;
import com.example.jwtrolebased.dto.UserResponse;
import com.example.jwtrolebased.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
//@RequestMapping("/login")
public class AuthController {

    private final LoginService loginService;

    @Autowired
    public AuthController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/register/login")
    public ResponseEntity<UserResponse> createJwtToken(@RequestBody LoginReq loginReq) throws Exception {
        return ResponseEntity.ok(loginService.createJwtToken(loginReq));
    }

    @GetMapping("/register/access")
    public ResponseEntity<?> allAccess() throws Exception {
        return ResponseEntity.ok(Map.of("status", "good"));
    }


}
