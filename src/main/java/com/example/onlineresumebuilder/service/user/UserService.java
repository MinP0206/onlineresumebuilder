package com.example.onlineresumebuilder.service.user;


import com.example.onlineresumebuilder.dtos.UserDto;
import com.example.onlineresumebuilder.enums.ResponseCode;
import com.example.onlineresumebuilder.exception.InternalException;
import com.example.onlineresumebuilder.exception.ResourceNotFoundException;
import com.example.onlineresumebuilder.model.Role;
import com.example.onlineresumebuilder.model.User;
import com.example.onlineresumebuilder.payload.UserProfile;
import com.example.onlineresumebuilder.payload.UserSummary;
import com.example.onlineresumebuilder.payload.resquest.UpdateUserRequest;
import com.example.onlineresumebuilder.repository.IRoleRepository;
import com.example.onlineresumebuilder.repository.UserRepository;
import com.example.onlineresumebuilder.security.UserPrincipal;
import com.example.onlineresumebuilder.utils.ModelMapperUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public List<UserDto> getAllUser() {
        int check = 0;
        List<User> notAdmin = userRepository.findAll();
        for (int i = notAdmin.size() - 1; i >= 0; i--) {
            for (Role element : notAdmin.get(i).getRoles()) {
                if (element.getId() == 2) {
                    check = 1;
                }
            }
            if (check == 1) {
                notAdmin.remove(i);
                check = 0;
            }

        }

//        return ModelMapperUtils.mapList(notAdmin, UserDto.class);
        return ModelMapperUtils.mapListUser(notAdmin);

    }

    @Override
    public UserSummary getCurrentUser(UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName(),currentUser.getEmail(), currentUser.getImage(), currentUser.getAuthorities().toString());
    }

    @Override
    public UserProfile getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getEmail(), user.getImage());
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);

    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserProfile updateInfoUser(UpdateUserRequest request) {
        User user = userRepository.findById(request.getId()).orElseThrow(() -> new InternalException(ResponseCode.USER_NOT_FOUND));
        if(!request.getName().isBlank()) user.setName(request.getName());
        if(!request.getImage().isBlank())  user.setImage(request.getImage());
        return ModelMapperUtils.mapper(userRepository.save(user), UserProfile.class);
    }

    @Override
    public Boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;

    }


}
