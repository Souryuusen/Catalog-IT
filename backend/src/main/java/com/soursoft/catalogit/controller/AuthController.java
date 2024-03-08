package com.soursoft.catalogit.controller;

import com.soursoft.catalogit.dto.UserDTO;
import com.soursoft.catalogit.exception.UserAlreadyExistException;
import com.soursoft.catalogit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
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
        var registerdUser = userService.registerUser(user);
        return UserDTO.fromUser(registerdUser);
    }

}
