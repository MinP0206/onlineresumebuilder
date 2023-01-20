package com.example.onlineresumebuilder.service.user;


import com.example.onlineresumebuilder.dtos.UserDto;
import com.example.onlineresumebuilder.payload.UserProfile;
import com.example.onlineresumebuilder.payload.UserSummary;
import com.example.onlineresumebuilder.payload.resquest.UpdateUserRequest;
import com.example.onlineresumebuilder.security.UserPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    List<UserDto> getAllUser();
    UserSummary getCurrentUser(UserPrincipal currentUser) ;
    UserProfile getUserProfile(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    UserProfile updateInfoUser(UpdateUserRequest request);
    Boolean deleteUserById(Long Id);

}
