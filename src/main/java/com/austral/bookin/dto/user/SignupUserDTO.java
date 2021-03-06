package com.austral.bookin.dto.user;

import com.austral.bookin.util.Patterns;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupUserDTO {

    @NotNull
    @Pattern(regexp = Patterns.PATTERN_NAME)
    private String firstName;

    @NotNull
    @Pattern(regexp = Patterns.PATTERN_NAME)
    private String lastName;

    @Pattern(regexp = Patterns.PATTERN_EMAIL)
    private String email;

    @Pattern(regexp = Patterns.PATTERN_PASSWORD)
    private String password;

    @Pattern(regexp = Patterns.PATTERN_GENDER)
    private String gender;
}