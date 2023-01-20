package com.example.onlineresumebuilder.controller;


import com.example.onlineresumebuilder.model.RoleName;
import com.example.onlineresumebuilder.payload.response.ApiResponse;
import com.example.onlineresumebuilder.payload.response.JwtAuthenticationResponse;
import com.example.onlineresumebuilder.payload.resquest.LoginRequest;
import com.example.onlineresumebuilder.payload.resquest.SignUpRequest;
import com.example.onlineresumebuilder.security.CurrentUser;
import com.example.onlineresumebuilder.security.UserPrincipal;
import com.example.onlineresumebuilder.service.auth.IAuthService;
import com.example.onlineresumebuilder.service.user.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth Controller", description = "Thao tác với auth")
public class AuthController {

    @Autowired
    IAuthService authService;

    @Autowired
    IUserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(new JwtAuthenticationResponse(authService.authenticateUser(loginRequest)));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.created(authService.registerUser(signUpRequest, RoleName.ROLE_USER)).body(new ApiResponse(true, "User registered successfully"));
    }
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/registerStaff")
//    public ResponseEntity<?> registerStaff(@Valid @RequestBody SignUpRequest signUpRequest) {
//        if (userService.existsByUsername(signUpRequest.getUsername())) {
//            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
//                HttpStatus.BAD_REQUEST);
//        }
//        if (userService.existsByEmail(signUpRequest.getEmail())) {
//            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
//                HttpStatus.BAD_REQUEST);
//        }
//        return ResponseEntity.created(authService.registerUser(signUpRequest, RoleName.ROLE_STAFF)).body(new ApiResponse(true, "User registered successfully"));
//    }

}
