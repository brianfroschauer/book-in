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
    private String firstName;
    private String lastName;
    private String nationality;
    private Date birthday;
    private byte[] photo;
}