package com.study.board.web.dto.websocket;

import com.study.board.web.dto.commentdto.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentWSForm {

    private Long postId;
    private String writer;
    private String comment;

    public CommentWSForm(CommentRequestDto requestDto) {
        this.postId = requestDto.getPostId();
        this.writer = requestDto.getUserId();
        this.comment = requestDto.getComment();
    }
}
