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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.username(), user.password()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return userService.findUserByUsername(user.username()).convertToDTO();
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDTO user) {
        if(userService.userExist(user)) {
            return new ResponseEntity<>("User with provided credentials already exist.", HttpStatus.BAD_REQUEST);
        }
        User registeredUser = this.userService.registerUser(user);
        return new ResponseEntity<>("User registered successfully.", HttpStatus.CREATED);
    }

}
