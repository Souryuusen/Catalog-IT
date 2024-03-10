package com.soursoft.catalogit.controller;

import com.soursoft.catalogit.dto.UserDTO;
import com.soursoft.catalogit.dto.UserLoginDTO;
import com.soursoft.catalogit.dto.UserRegisterDTO;
import com.soursoft.catalogit.entity.User;
import com.soursoft.catalogit.exception.UserAlreadyExistException;
import com.soursoft.catalogit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.core.Authentication;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public UserDTO authenticateUser(@RequestBody UserLoginDTO user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.username(), user.password()));
        return userService.findUserByUsername(user.username()).convertToDTO();
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDTO user) {
        if(userService.userExist(user)) {
            return new ResponseEntity<>("User with provided credentials already exist.", HttpStatus.BAD_REQUEST);
        }
        User registeredUser = this.userService.registerUser(user);
        return new ResponseEntity<>(registeredUser.convertToDTO(), HttpStatusCode.valueOf(201));
    }

}
