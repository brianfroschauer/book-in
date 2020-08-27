package com.austral.bookin.controller;

import com.austral.bookin.dto.user.UpdateUserDTO;
import com.austral.bookin.dto.user.UserDTO;
import com.austral.bookin.entity.User;
import com.austral.bookin.service.user.UserService;
import com.austral.bookin.specification.UserSpecification;
import com.austral.bookin.util.ObjectMapper;
import com.austral.bookin.util.ObjectMapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserController(UserService userService) {
        this.userService = userService;
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
    public ResponseEntity<UserDTO> findMe(Principal principal) {
        final User user = userService.find(principal);
        return ResponseEntity.ok(objectMapper.map(user, UserDTO.class));
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