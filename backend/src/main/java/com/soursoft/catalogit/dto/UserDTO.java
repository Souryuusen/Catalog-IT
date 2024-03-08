package com.soursoft.catalogit.dto;

import com.soursoft.catalogit.entity.Role;
import com.soursoft.catalogit.entity.User;

import java.util.Set;

public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String authToken;
    private Set<Role> roles;

    //todo: Create logic for auth token
    public static UserDTO fromUser(User user) {
        var dto = new UserDTO();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles());
        dto.setAuthToken("test");
        return dto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
