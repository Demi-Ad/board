package com.study.board.web.dto.boarddto;

import com.study.board.domain.entity.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardListDto {

    private Long id;
    private String title;
    private String nickname;
    private int postHit;
    private LocalDateTime createdDate;

    public BoardListDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.nickname = post.getUser().getNickname();
        this.postHit = post.getCount();
        this.createdDate = post.getCreatedDate();
    }
}
