package com.austral.bookin.controller;

import com.austral.bookin.dto.user.SignupUserDTO;
import com.austral.bookin.dto.user.UserDTO;
import com.austral.bookin.entity.User;
import com.austral.bookin.service.signup.SignupService;
import com.austral.bookin.service.user.UserService;
import com.austral.bookin.util.MailStrategy;
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
    private final UserService userService;

    public SignupController(SignupService signupService, UserService userService) {
        this.signupService = signupService;
        this.userService = userService;
        this.objectMapper = new ObjectMapperImpl();
    }

    @PostMapping
    public ResponseEntity<UserDTO> signup(@RequestBody @Valid SignupUserDTO signupUser) {
        final User user = signupService.signup(objectMapper.map(signupUser, User.class));
        userService.sendMail(MailStrategy.REGISTER, user);
        return ResponseEntity.ok(objectMapper.map(user, UserDTO.class));
    }
}
