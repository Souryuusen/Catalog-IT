package com.soursoft.catalogit.controller;

import com.soursoft.catalogit.dto.UserDTO;
import com.soursoft.catalogit.exception.UserAlreadyExistException;
import com.soursoft.catalogit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/user-auth", consumes = "application/json", produces = "application/json")
    public UserDTO authenticateUser(@RequestBody UserDTO user) {
        return new UserDTO();
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public UserDTO registerUser(@RequestBody UserDTO user) {
        if(userService.userExist(user)) {
            throw new UserAlreadyExistException("User with provided credentials already exist!");
        }
        var registeredUser = userService.registerUser(user);
        String toEncode = registeredUser.getUsername() + ":" + registeredUser.getPassword().substring(registeredUser.getPassword().length()-31, registeredUser.getPassword().length());
        var tempAuthToken = Base64.getEncoder().encodeToString(toEncode.getBytes(StandardCharsets.UTF_8));
        var registeredDTO = UserDTO.fromUser(registeredUser);
        registeredDTO.setAuthToken(tempAuthToken);
        return registeredDTO;
    }

}
