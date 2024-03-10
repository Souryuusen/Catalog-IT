package com.soursoft.catalogit.dto;

import com.soursoft.catalogit.entity.Role;

import java.util.Set;

public record UserDTO(long userId, String username, String email, Set<Role> roles) {
}
