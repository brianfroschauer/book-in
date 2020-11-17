package com.austral.bookin.dto.book;

import com.austral.bookin.dto.author.RankingAuthorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBookWithAuthorsDTO {

    private Long id;
    private String title;
    private String genre;
    private byte[] photo;
    private float stars;
    private List<RankingAuthorDTO> authors;
}
