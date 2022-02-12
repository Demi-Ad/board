package com.study.board.domain.entity.post;

import com.study.board.domain.entity.BaseEntity;
import com.study.board.domain.entity.comment.Comment;
import com.study.board.domain.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Lob
    private String contents;

    @Column(name = "post_count")
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx",foreignKey = @ForeignKey(name = "user_post_fk"))
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    public Post(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void changePost(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void userPublish(User user) {
        this.user = user;
    }

    public void postHit() {
        this.count += 1;
    }
}
