package com.study.board.web.dto.commentdto;

import com.study.board.domain.entity.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {
    private String nickname;
    private String commentText;
    private String lastModifiedDate;
    private long commentId;
    private boolean isDeletable;


    public CommentResponseDto(Comment comment,boolean isDeletable) {
        this.commentId = comment.getId();
        this.nickname = comment.getUser().getNickname();
        this.commentText = comment.getContent();
        this.lastModifiedDate = comment.getUpdatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.isDeletable = isDeletable;
    }
}
