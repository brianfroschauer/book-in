package com.austral.bookin.dto.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String nationality;
    private Date date_of_birth;
    private String photo;
}
