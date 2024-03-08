package com.soursoft.catalogit.service;

import com.soursoft.catalogit.entity.Role;
import com.soursoft.catalogit.entity.User;
import com.soursoft.catalogit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserService implements UserDetailsService {

    private UserRepository repositiory;

    @Autowired
    public UserService(UserRepository repositiory) {
        this.repositiory = repositiory;
    }

    public User findUserByUsername(String username) {
        return this.repositiory.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var foundUser = findUserByUsername(username);
        if(foundUser == null) {
            throw new UsernameNotFoundException("Username " + username + " has not been found!");
        } else {
            return new org.springframework.security.core.userdetails.User(foundUser.getUsername(),
                            foundUser.getPassword(), mapRolesToAuthorities(foundUser.getUserRoles()));
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                    .collect(Collectors.toList());
    }
}
