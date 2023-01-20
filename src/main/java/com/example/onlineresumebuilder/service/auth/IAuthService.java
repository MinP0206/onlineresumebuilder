package com.example.onlineresumebuilder.service.auth;

import com.example.onlineresumebuilder.model.RoleName;
import com.example.onlineresumebuilder.payload.resquest.LoginRequest;
import com.example.onlineresumebuilder.payload.resquest.SignUpRequest;
import com.example.onlineresumebuilder.security.UserPrincipal;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public interface IAuthService {
    String authenticateUser(LoginRequest loginRequest);
    URI registerUser(SignUpRequest signUpRequest, RoleName roleName);


}
