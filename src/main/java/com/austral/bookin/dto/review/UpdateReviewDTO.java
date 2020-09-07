package com.austral.bookin.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReviewDTO {

    @Range(min = 1, max = 5)
    private Integer stars; /* Using Integer because int can't be null */

    @Size(max = 1000)
    private String comment;
}