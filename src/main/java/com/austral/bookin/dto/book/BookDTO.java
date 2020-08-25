package com.austral.bookin.dto.book;

import com.austral.bookin.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;
    private String title;
    private String genre;
    private String language;
    private Date date;
    private byte[] photo;
    private List<Author> authors;
}