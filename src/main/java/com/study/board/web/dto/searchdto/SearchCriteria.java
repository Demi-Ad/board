package com.study.board.web.dto.searchdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    SearchType searchType;

    @NotBlank
    @NotNull
    @Size(min=2)
    String searchText;
}
