package com.austral.bookin.dto.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingAuthorDTO {

    private Long id;
    private String firstName;
    private String lastName;
}
