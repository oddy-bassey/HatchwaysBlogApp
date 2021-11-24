package com.HatchwaysBlogApp.service;

import com.HatchwaysBlogApp.dto.AuthenticationResponse;
import com.HatchwaysBlogApp.dto.LoginRequest;
import com.HatchwaysBlogApp.dto.RegisterRequest;
import com.HatchwaysBlogApp.model.User;

import java.util.Optional;

public interface AuthenticationService {

    void signup(RegisterRequest registerRequest);

    User getCurrentUser();

    AuthenticationResponse login(LoginRequest loginRequest);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    String getToken(LoginRequest loginRequest);

    boolean isLoggedIn();
}
