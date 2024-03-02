package com.soursoft.catalogit.controller;

import com.soursoft.catalogit.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/auth")
public class AuthController {

    public UserDTO authenticateUser(@RequestBody UserDTO user) {
        return new UserDTO();
    }


}
