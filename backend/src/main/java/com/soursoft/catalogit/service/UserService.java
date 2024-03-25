package com.soursoft.catalogit.service;

import com.soursoft.catalogit.dto.MovieDataDTO;
import com.soursoft.catalogit.dto.UserDTO;
import com.soursoft.catalogit.dto.UserRegisterDTO;
import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.entity.Role;
import com.soursoft.catalogit.entity.User;
import com.soursoft.catalogit.entity.WatchlistElement;
import com.soursoft.catalogit.exception.UserByIdNotFoundException;
import com.soursoft.catalogit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    public User findUserByUsername(String username) {
        return this.repository.findByUsernameIgnoreCase(username);
    }

    public User findUserById(Long userId) {
        var result = this.repository.findById(userId);
        if(result.isPresent()) {
            return result.get();
        } else {
            throw new UserByIdNotFoundException("User by ID: " + userId + " has not been found!");
        }
    }

    public boolean userExist(UserRegisterDTO user) {
        var foundUsername = this.repository.findByUsernameIgnoreCase(user.username());
        var foundEmail = this.repository.findByEmailIgnoreCase(user.email());

        return (foundUsername != null || foundEmail != null);
    }

    public User registerUser(UserRegisterDTO userData) {
        User userToRegister = new User();
        userToRegister.setUsername(userData.username());
        userToRegister.setEmail(userData.email());
        userToRegister.setPassword(passwordEncoder.encode(userData.password()));
        return this.repository.save(userToRegister);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var foundUser = findUserByUsername(username);
        if(foundUser == null) {
            throw new UsernameNotFoundException("Username " + username + " has not been found!");
        } else {
            return new org.springframework.security.core.userdetails.User(foundUser.getUsername(),
                            foundUser.getPassword(), mapRolesToAuthorities(foundUser.getRoles()));
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                    .collect(Collectors.toList());
    }
}
