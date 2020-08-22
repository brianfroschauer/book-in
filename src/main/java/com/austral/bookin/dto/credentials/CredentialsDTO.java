package com.austral.bookin.dto.credentials;

import com.austral.bookin.util.Patterns;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDTO {

    @Pattern(regexp = Patterns.PATTERN_EMAIL)
    private String email;

    @Pattern(regexp = Patterns.PATTERN_PASSWORD)
    private String password;
}