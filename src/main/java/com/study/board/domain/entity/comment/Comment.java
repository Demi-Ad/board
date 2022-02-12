package com.study.board.domain.entity.comment;

import com.study.board.domain.entity.BaseEntity;
import com.study.board.domain.entity.post.Post;
import com.study.board.domain.entity.user.User;
import com.study.board.web.dto.commentdto.CommentResponseDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx",foreignKey = @ForeignKey(name = "user_comment_fk"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",foreignKey = @ForeignKey(name = "post_comment_fk"))
    private Post post;



    public Comment(String content, User user,Post post) {
        this.content = content;
        this.user = user;
        setPost(post);
    }

    private void setPost(Post post) {
        this.post = post;
        post.getCommentList().add(this);
    }

    public CommentResponseDto toResponseDto() {
        return CommentResponseDto.builder()
                .nickname(this.user.getNickname())
                .commentText(this.content)
                .lastModifiedDate(this.getUpdatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
