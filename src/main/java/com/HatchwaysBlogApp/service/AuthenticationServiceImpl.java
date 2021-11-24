package com.HatchwaysBlogApp.service;

import com.HatchwaysBlogApp.dto.AuthenticationResponse;
import com.HatchwaysBlogApp.dto.LoginRequest;
import com.HatchwaysBlogApp.dto.RegisterRequest;
import com.HatchwaysBlogApp.dto.UserDto;
import com.HatchwaysBlogApp.exceptions.BlogAppException;
import com.HatchwaysBlogApp.model.User;
import com.HatchwaysBlogApp.repository.UserRepository;
import com.HatchwaysBlogApp.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public void signup(RegisterRequest registerRequest) {
        User user = User.builder()
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .created(Instant.now())
                .lastUpdated(Instant.now())
                .enabled(true).build();

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {

        User user = findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new BlogAppException("user does not exist"));

        return AuthenticationResponse.builder()
                .authenticationToken(getToken(loginRequest))
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .user(UserDto.builder()
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build())
                .build();
    }
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String getToken(LoginRequest loginRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return jwtProvider.generateToken(authenticate);
    }

    @Override
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}