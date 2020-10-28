package com.austral.bookin.dto.user;

import com.austral.bookin.util.Patterns;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class ChangeUserPasswordDTO {

    private String oldPassword;

    @Pattern(regexp = Patterns.PATTERN_PASSWORD)
    private String password;
}
