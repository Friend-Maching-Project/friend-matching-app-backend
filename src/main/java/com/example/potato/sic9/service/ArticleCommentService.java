package com.example.potato.sic9.service;

import com.example.potato.sic9.dto.articleComment.ArticleCommentRequestDto;
import com.example.potato.sic9.dto.articleComment.ArticleCommentResponseDto;
import com.example.potato.sic9.entity.Article;
import com.example.potato.sic9.entity.ArticleComment;
import com.example.potato.sic9.entity.User;
import com.example.potato.sic9.repository.ArticleCommentRepository;
import com.example.potato.sic9.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;

    public ArticleCommentResponseDto saveArticleComment(User user, ArticleCommentRequestDto dto) {
        Article article = articleRepository.findById(dto.getArticleId()).orElseThrow(); // Todo: 추후 예외 처리 추가
        ArticleComment articleComment = articleCommentRepository.save(dto.toEntity(user, article));
        return ArticleCommentResponseDto.from(articleComment);
    }

    public void deleteArticleComment(User user, Long id) {
        if (Objects.equals(user.getId(), articleCommentRepository.findById(id).orElseThrow().getUser().getId())) {
            articleCommentRepository.deleteById(id);
        }
        // Todo : 추후 예외 처리 (다른 사용자가 삭제 할 경우, id에 맞는 게시물이 없는 경우)
    }

}
