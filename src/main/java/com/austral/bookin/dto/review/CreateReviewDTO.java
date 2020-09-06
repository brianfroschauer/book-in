package com.austral.bookin.dto.review;

import com.austral.bookin.dto.book.BookDTO;
import com.austral.bookin.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewDTO {

    @NotNull
    @Range(min = 1, max = 5)
    private int stars;

    @Size(max = 1000)
    private String comment;

    @NotNull
    private UserDTO user;

    @NotNull
    private BookDTO book;
}