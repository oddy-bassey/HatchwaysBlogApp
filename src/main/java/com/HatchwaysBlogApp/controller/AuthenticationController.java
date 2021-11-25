package com.HatchwaysBlogApp.controller;

import com.HatchwaysBlogApp.dto.AuthenticationResponse;
import com.HatchwaysBlogApp.dto.LoginRequest;
import com.HatchwaysBlogApp.dto.RegisterRequest;
import com.HatchwaysBlogApp.exceptions.BlogAppException;
import com.HatchwaysBlogApp.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid RegisterRequest registerRequest) {

        if(authService.findByUsername(registerRequest.getUsername()).isPresent()){
            throw new BlogAppException(String.format("username: %s already exists!", registerRequest.getUsername()));

        }else if(authService.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new BlogAppException(String.format("email: %s already exists!", registerRequest.getEmail()));
        }
        authService.signup(registerRequest);

        return new ResponseEntity<>("User Registration Successful", OK);
    }

    @PostMapping("/signin")
    public AuthenticationResponse signin(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
