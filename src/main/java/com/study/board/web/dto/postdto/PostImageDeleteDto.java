package com.study.board.web.dto.postdto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PostImageDeleteDto {
    private List<String> deleteImages;
}
