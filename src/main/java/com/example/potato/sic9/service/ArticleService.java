package com.example.potato.sic9.service;

import com.example.potato.sic9.dto.article.ArticleRequestDto;
import com.example.potato.sic9.dto.article.ArticleResponseDto;
import com.example.potato.sic9.entity.Article;
import com.example.potato.sic9.entity.User;
import com.example.potato.sic9.repository.ArticleRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public void saveArticle(ArticleRequestDto dto) {
        Article article = Article.of(dto);
        articleRepository.save(article);
    }

    public ArticleResponseDto getArticle(Long id) {
        return ArticleResponseDto.from(articleRepository.findById(id).orElseThrow());
    }

    public List<ArticleResponseDto> getArticles(Long lastArticleId, Pageable pageable) {
        return articleRepository
                .searchAll(lastArticleId, pageable)
                .stream().map(ArticleResponseDto::from)
                .collect(Collectors.toList());
    }

    public void deleteArticle(User user, Long id) {
        if (Objects.equals(user.getId(), articleRepository.findById(id).orElseThrow().getUser().getId())) {
            articleRepository.deleteById(id);
        }
        // Todo: 추후 예외 처리 추가
    }

    public ArticleResponseDto updateArticle(User user, Long id, ArticleRequestDto dto) {
        Article article = articleRepository.findById(id).orElseThrow();
        if (Objects.equals(user.getId(), article.getUser().getId())) {
            if (dto.getComment() != null) {
                article.setComment(dto.getComment());
            }
            if (dto.getPlace() != null) {
                article.setPlace(dto.getPlace());
            }
            if (dto.getMeetAt() != null) {
                article.setMeetAt(dto.getMeetAt());
            }
            return ArticleResponseDto.from(articleRepository.save(article));
        }
        return null; // Todo: 추후 예외 처리 추가
    }

    public List<ArticleResponseDto> getArticlesWithFilters(Long lastArticleId, List<String> places, List<String> sex,
                                                           Pageable pageable) {
        return articleRepository.searchAllWithFilter(lastArticleId, places, sex, pageable).stream()
                .map(ArticleResponseDto::from)
                .collect(Collectors.toList());
    }
}
