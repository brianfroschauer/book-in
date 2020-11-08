package com.austral.bookin.controller;

import com.austral.bookin.dto.user.ChangeUserPasswordDTO;
import com.austral.bookin.dto.user.UpdateUserDTO;
import com.austral.bookin.dto.user.UserDTO;
import com.austral.bookin.dto.user.UserNewPasswordDTO;
import com.austral.bookin.entity.Token;
import com.austral.bookin.entity.User;
import com.austral.bookin.exception.ExpiredTokenException;
import com.austral.bookin.exception.InvalidOldPasswordException;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.service.token.TokenService;
import com.austral.bookin.service.user.UserService;
import com.austral.bookin.specification.UserSpecification;
import com.austral.bookin.util.ObjectMapper;
import com.austral.bookin.util.ObjectMapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.objectMapper = new ObjectMapperImpl();
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> find(UserSpecification specification) {
        final List<User> users = userService.find(specification);
        return ResponseEntity.ok(objectMapper.map(users, UserDTO.class));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> find(@PathVariable Long id) {
        final User user = userService.find(id);
        return ResponseEntity.ok(objectMapper.map(user, UserDTO.class));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> find(Principal principal) {
        if (principal == null) return null;
        final User user = userService.find(principal);
        return ResponseEntity.ok(objectMapper.map(user, UserDTO.class));
    }

    @PostMapping("/resetPassword")
    public HttpStatus resetUserPassword(@RequestParam("email") String email) {
        try {
            final User user = userService.find(email);
            final Token token = tokenService.createPasswordResetToken(user);
            userService.sendMail(token);
            return HttpStatus.OK;
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This users doesn't exist");
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There was a problem saving the token");
        }
    }

    @GetMapping("/validateToken")
    public ResponseEntity<UserDTO> validateToken(@RequestParam("token") String tokenReceived) {
        try {
            Token token = tokenService.find(tokenReceived);
            tokenService.validateToken(token);
            return ResponseEntity.ok(objectMapper.map(token.getUser(), UserDTO.class));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The token doesn't exist");
        } catch (ExpiredTokenException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The token is expired");
        }
    }

    @PostMapping("/newPassword")
    public ResponseEntity<UserDTO> setNewPassword(@Valid @RequestBody UserNewPasswordDTO userNewPasswordDTO) {
        User user = userService.setPassword(userNewPasswordDTO.getId(), userNewPasswordDTO.getPassword());
        return ResponseEntity.ok(objectMapper.map(user, UserDTO.class));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<UserDTO> changeUserPassword(@Valid @RequestBody ChangeUserPasswordDTO changeUserPasswordDTO) {

        User user = userService.find(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            user = userService.updatePassword(changeUserPasswordDTO.getOldPassword(), changeUserPasswordDTO.getPassword(), user);
            return ResponseEntity.ok(objectMapper.map(user, UserDTO.class));
        } catch (InvalidOldPasswordException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The old password is wrong");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id,
                                          @RequestPart("user") @Valid UpdateUserDTO updateUserDTO,
                                          @RequestPart(value = "photo", required = false) MultipartFile file) {
        final User user = userService.update(id, objectMapper.map(updateUserDTO, User.class), file);
        return ResponseEntity.ok(objectMapper.map(user, UserDTO.class));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
