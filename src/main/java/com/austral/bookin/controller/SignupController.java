package com.austral.bookin.controller;

import com.austral.bookin.dto.user.SignupUserDTO;
import com.austral.bookin.dto.user.UserDTO;
import com.austral.bookin.entity.User;
import com.austral.bookin.service.signup.SignupService;
import com.austral.bookin.util.ObjectMapper;
import com.austral.bookin.util.ObjectMapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("signup")
public class SignupController {

    private final SignupService signupService;
    private final ObjectMapper objectMapper;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
        this.objectMapper = new ObjectMapperImpl();
    }

    @PostMapping
    public ResponseEntity<UserDTO> signup(@RequestBody @Valid SignupUserDTO signupUser) {
        final User user = signupService.signup(objectMapper.map(signupUser, User.class));
        return ResponseEntity.ok(objectMapper.map(user, UserDTO.class));
    }
}