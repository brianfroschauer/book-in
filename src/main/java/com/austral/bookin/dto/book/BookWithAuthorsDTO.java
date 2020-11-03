package com.austral.bookin.dto.book;

import com.austral.bookin.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookWithAuthorsDTO {

    private Long id;
    private String title;
    private String genre;
    private byte[] photo;
    private float stars;
    private Author[] authors;
}