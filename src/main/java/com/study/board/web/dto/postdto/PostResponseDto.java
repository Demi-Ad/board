package com.study.board.web.dto.postdto;

import com.study.board.web.dto.commentdto.CommentResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime lastModifiedDate;
    private boolean isUpdatable;

    private List<CommentResponseDto> commentList = new ArrayList<>();

    @Builder
    public PostResponseDto(Long id, String title, String content, String author, LocalDateTime lastModifiedDate,
                           boolean isUpdatable, List<CommentResponseDto> commentList) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.lastModifiedDate = lastModifiedDate;
        this.isUpdatable = isUpdatable;
        this.commentList.addAll(commentList);
    }
}
