package com.example.onlineresumebuilder.service.auth;

import com.example.onlineresumebuilder.enums.ResponseCode;
import com.example.onlineresumebuilder.exception.AppException;
import com.example.onlineresumebuilder.exception.InternalException;
import com.example.onlineresumebuilder.model.Role;
import com.example.onlineresumebuilder.model.RoleName;
import com.example.onlineresumebuilder.model.User;
import com.example.onlineresumebuilder.payload.resquest.LoginRequest;
import com.example.onlineresumebuilder.payload.resquest.SignUpRequest;
import com.example.onlineresumebuilder.repository.IRoleRepository;
import com.example.onlineresumebuilder.repository.UserRepository;
import com.example.onlineresumebuilder.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;


@Service
public class AuthService implements IAuthService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public String authenticateUser(LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsernameOrEmail(),
                    loginRequest.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return tokenProvider.generateToken(authentication); //jwt
        } catch (Exception e){
            throw new InternalException(ResponseCode.LOGIN_FAIL);
        }

    }

    @Override
    public URI registerUser(SignUpRequest signUpRequest, RoleName roleName) {
        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
            signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(roleName)
            .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        //return URI
        return ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/users/{username}")
            .buildAndExpand(result.getUsername()).toUri();
    }




}
