package com.example.catshelterservice.controllers;

import com.example.catshelterservice.config.JwtTokenUtil;
import com.example.catshelterservice.dto.CredentialDTO;
import com.example.catshelterservice.dto.TokenDTO;
import com.example.catshelterservice.models.Role;
import com.example.catshelterservice.models.User;
import com.example.catshelterservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "${frontend.endpoint}")
@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/login")
    public TokenDTO login(@RequestBody CredentialDTO credential) {
        Authentication authentication;
        try {
            authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(credential.getLogin(), credential.getPassword()));
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "400 - bad credentials", ex
            );
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new TokenDTO(tokenUtil.generateToken(userDetailsService.loadUserByUsername(credential.getLogin())));
    }

    @PostMapping("/logout")
    public void logout() {
        SecurityContextHolder.clearContext();
    }

    @PostMapping("/register")
    public TokenDTO register(@RequestBody User user){
        Set<Role> set = new HashSet<>();
        set.add(new Role(1L, "ROLE_USER"));

        String password = user.getPassword();

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setAuthorities(set);
        userRepository.save(user);

        return login(new CredentialDTO(user.getEmail(), password));
    }
}
