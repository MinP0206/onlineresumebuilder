package com.example.onlineresumebuilder.dtos;


import com.example.onlineresumebuilder.model.User;
import com.example.onlineresumebuilder.model.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends DateAudit {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String image;
    private String role;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.name = user.getName();
        this.image = user.getImage();
        this.role = user.getRoles().stream().findFirst().get().getName().toString();
    }

}
