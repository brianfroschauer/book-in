package com.austral.bookin.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewWithBookDTO {

    private Long id;
    private int stars;
    private String comment;
    private Long bookId;
    private String bookTitle;
}