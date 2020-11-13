package com.austral.bookin.dto.book;

import com.austral.bookin.dto.author.AuthorDTO;
import com.austral.bookin.util.Patterns;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookDTO {

    @Pattern(regexp = Patterns.PATTERN_TITLE)
    private String title;

    @Pattern(regexp = Patterns.PATTERN_GENRE)
    private String genre;

    @Pattern(regexp = Patterns.PATTERN_LANGUAGE)
    private String language;

    @Past
    private Date date;

    private List<Long> authors;
}
