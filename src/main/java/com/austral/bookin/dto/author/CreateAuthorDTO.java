package com.austral.bookin.dto.author;

import com.austral.bookin.util.Patterns;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAuthorDTO {

    @NotNull
    @Pattern(regexp = Patterns.PATTERN_NAME)
    private String firstName;

    @NotNull
    @Pattern(regexp = Patterns.PATTERN_NAME)
    private String lastName;
    private String nationality;
    @Past
    private Date birthday;
}