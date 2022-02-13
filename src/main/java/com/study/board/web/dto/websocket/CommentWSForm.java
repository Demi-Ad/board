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
    private String title;

    public CommentWSForm(CommentRequestDto requestDto,String title,String userNickname) {
        this.postId = requestDto.getPostId();
        this.writer = userNickname;
        this.comment = requestDto.getComment();
        this.title = title;
    }
}
