package com.example.potato.sic9.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
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

    @Column(nullable = false, name = "user_age")
    private Integer age;

    @Column(nullable = false, name = "user_sex")
    private String sex;

    @Column(nullable = false, name = "user_department")
    private String department;

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
                Integer age,
                String sex,
                String department,
                Authority authority,
                LocalDateTime createdAt,
                List<Article> articles,
                List<ArticleComment> articleComments) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
        this.sex = sex;
        this.department = department;
        this.authority = authority;
        this.createdAt = createdAt;
        this.articles = articles;
        this.articleComments = articleComments;
    }
}
