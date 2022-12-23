package com.example.potato.sic9.entity;

import com.example.potato.sic9.common.AuthProvider;
import com.example.potato.sic9.common.Authority;
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

    @Column(name = "user_oauth_id")
    private String OAuthId;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    @Setter
    private String password;

    @Column(name = "user_nickname")
    @Setter
    private String nickname;

    @Column(name = "user_birth")
    @Setter
    private LocalDate birth;

    @Column(name = "user_sex")
    @Setter
    private String sex;

    @Column(name = "user_major")
    @Setter
    private String major;

    @Enumerated(EnumType.STRING) // enum 이름은 DB에 저장
    @Column(nullable = false, name = "user_authority")
    private Authority authority;


    @Enumerated(EnumType.STRING)
    @Column(name = "user_auth_provider")
    private AuthProvider authProvider;

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
                String OAuthId,
                String email,
                String password,
                String nickname,
                LocalDate birth,
                String sex,
                String major,
                Authority authority,
                AuthProvider authProvider,
                LocalDateTime createdAt,
                List<Article> articles,
                List<ArticleComment> articleComments) {
        this.id = id;
        this.OAuthId = OAuthId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.birth = birth;
        this.sex = sex;
        this.major = major;
        this.authority = authority;
        this.authProvider = authProvider;
        this.createdAt = createdAt;
        this.articles = articles;
        this.articleComments = articleComments;
    }
}
