package com.study.board.web.dto.postdto;

import com.study.board.domain.entity.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

    private String userId;

    private String userNickname;

    @NotBlank
    @Size(min = 2)
    private String title;
    @Size(min = 2)
    private String contents;


    public Post toEntity() {
        return new Post(title, contents);
    }

    public PostDto(PostResponseDto postResponseDto, String userId) {
        this.userId = userId;
        this.userNickname = postResponseDto.getAuthor();
        this.title = postResponseDto.getTitle();
        this.contents = postResponseDto.getContent();
    }

    public PostDto(Post post) {
        this.userId = post.getUser().getUserId();
        this.userNickname = post.getUser().getNickname();
        this.title = post.getTitle();
        this.contents = post.getContents();
    }

}
