package com.austral.bookin.dto.author;

import com.austral.bookin.util.Patterns;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupAuthorDTO {

    @NotNull
    @Size(min = 3, max = 30)
    @Pattern(regexp = Patterns.NAME_LASTNAME)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 30)
    @Pattern(regexp = Patterns.NAME_LASTNAME)
    private String lastName;
    private String nationality;
    @Past
    private Date birthday;
}
