package com.study.board.web.dto.userdto;

import com.study.board.domain.entity.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class UserPublishedPostDto {

    private Long id;
    private String title;
    private int postHit;
    private LocalDateTime lastModifiedDate;

    public UserPublishedPostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.postHit = post.getCount();
        this.lastModifiedDate = post.getUpdatedDate();
    }
}
