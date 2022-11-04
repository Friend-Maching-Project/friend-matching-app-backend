package com.example.potato.sic9.dto.articleComment;

import com.example.potato.sic9.dto.user.UserResponseDto;
import com.example.potato.sic9.entity.ArticleComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCommentResponseDto {
    private Long articleCommentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long articleId;
    private UserResponseDto user;

    public static ArticleCommentResponseDto from(ArticleComment entity) {
        return new ArticleCommentResponseDto(
                entity.getId(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getArticle().getId(),
                UserResponseDto.of(entity.getUser())
        );
    }
}
