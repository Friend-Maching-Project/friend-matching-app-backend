package com.example.potato.sic9.dto.articleComment;

import com.example.potato.sic9.entity.Article;
import com.example.potato.sic9.entity.ArticleComment;
import com.example.potato.sic9.entity.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArticleCommentRequestDto {
    private Long articleId;
    private String content;

    public ArticleComment toEntity(User user, Article article) {
        return new ArticleComment(content, article, user);
    }
}
