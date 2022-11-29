package com.example.potato.sic9.entity;

import com.example.potato.sic9.dto.article.ArticleRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Column(nullable = false, name = "article_place")
    @Setter
    private String place;

    @Column(name = "article_comment")
    @Setter
    private String comment;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false, name = "article_created_at")
    private LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(nullable = false, name = "article_modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "article_meet_at")
    @Setter
    private String meetAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @ToString.Exclude
    @Setter
    private List<ArticleComment> articleComments = new ArrayList<>();


    @Builder
    public Article(Long id, String place, String comment, LocalDateTime createdAt, LocalDateTime modifiedAt, String meetAt, User user, List<ArticleComment> articleComments) {
        this.id = id;
        this.place = place;
        this.comment = comment;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.meetAt = meetAt;
        this.user = user;
        this.articleComments = articleComments;
    }

    public Article(String place, String comment, String meetAt, User user) {
        this.place = place;
        this.comment = comment;
        this.meetAt = meetAt;
        this.user = user;
    }

    public static Article of(ArticleRequestDto dto) {
        return new Article(dto.getPlace(), dto.getComment(), dto.getMeetAt(), dto.getUser());
    }
}
