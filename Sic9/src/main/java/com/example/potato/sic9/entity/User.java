package com.example.potato.sic9.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Builder
@NoArgsConstructor
@ToString
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, name = "user_email")
    private String email;

    @Column(nullable = false, name = "user_password")
    @Setter
    private String password;

    @Column(nullable = false, name = "user_nickname")
    @Setter
    private String nickname;

    @Column(nullable = false, name = "user_birth")
    private LocalDate birth;

    @Column(nullable = false, name = "user_sex")
    private String sex;

    @Column(nullable = false, name = "user_major")
    private String major;

    @Enumerated(EnumType.STRING) // enum 이름은 DB에 저장
    @Column(nullable = false, name = "user_authority")
    private Authority authority;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false, name = "user_created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    @ToString.Exclude
    @Setter
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    @ToString.Exclude
    @Setter
    private List<ArticleComment> articleComments = new ArrayList<>();

    @Builder
    public User(Long id,
                String email,
                String password,
                String nickname,
                LocalDate birth,
                String sex,
                String major,
                Authority authority,
                LocalDateTime createdAt,
                List<Article> articles,
                List<ArticleComment> articleComments) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.birth = birth;
        this.sex = sex;
        this.major = major;
        this.authority = authority;
        this.createdAt = createdAt;
        this.articles = articles;
        this.articleComments = articleComments;
    }
}
