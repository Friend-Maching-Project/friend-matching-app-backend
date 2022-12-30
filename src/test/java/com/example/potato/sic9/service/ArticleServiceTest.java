package com.example.potato.sic9.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

import com.example.potato.sic9.common.Authority;
import com.example.potato.sic9.dto.article.ArticleRequestDto;
import com.example.potato.sic9.dto.article.ArticleResponseDto;
import com.example.potato.sic9.entity.Article;
import com.example.potato.sic9.entity.User;
import com.example.potato.sic9.repository.ArticleRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    void 게시글_단일조회() {
        // given
        Long articleId = 1L;
        Article article = createArticle();
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        // when
        ArticleResponseDto dto = articleService.getArticle(articleId);

        // then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("comment", article.getComment())
                .hasFieldOrPropertyWithValue("place", article.getPlace())
                .hasFieldOrPropertyWithValue("meetAt", article.getMeetAt());
        then(articleRepository).should().findById(articleId);
    }

    @Test
    @DisplayName("필터없이 게시글 목록을 조회하면, 게시글 목록을 반환한다")
    void 게시글_목록_조회_필터없이() {
        // given
        Long lastArticleId = 0L;
        Article article = createArticle();
        PageRequest pageRequest = PageRequest.of(0, 1, Direction.ASC, "createdAt");
        given(articleRepository.searchAll(lastArticleId, pageRequest)).willReturn(List.of(article));

        // when
        List<ArticleResponseDto> dtoList = articleService.getArticles(0L, pageRequest);

        // then
        assertThat(dtoList)
                .isNotEmpty()
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("comment", article.getComment());
        then(articleRepository).should().searchAll(lastArticleId, pageRequest);
    }

    @Test
    @DisplayName("필터있이 게시글 목록을 조회하면, 게시글 목록을 반환한다.")
    void 게시글_목록_조회_필터있이() {
        // given
        Long lastArticleId = 0L;
        Article article = createArticle();
        PageRequest pageRequest = PageRequest.of(0, 1, Direction.ASC, "createdAt");
        List<String> place = Arrays.asList("1", "2");
        List<String> sex = Arrays.asList("male", "female");
        given(articleRepository.searchAllWithFilter(lastArticleId, place, sex, pageRequest)).willReturn(
                List.of(article));

        // when
        List<ArticleResponseDto> dtoList = articleService.getArticlesWithFilters(lastArticleId, place, sex,
                pageRequest);

        // then
        assertThat(dtoList)
                .isNotEmpty()
                .hasSize(1)
                .first().hasFieldOrPropertyWithValue("comment", article.getComment());
        then(articleRepository).should().searchAllWithFilter(lastArticleId, place, sex, pageRequest);
    }

    @Test
    @DisplayName("게시글 정보를 입력하면, 게시글을 저장한다.")
    void 게시글_저장() {
        // given
        ArticleRequestDto dto = createArticleRequestDto("test", "test", "test");
        given(articleRepository.save(any(Article.class))).willReturn(null);

        // when
        articleService.saveArticle(dto);

        // then
        then(articleRepository).should().save(any(Article.class));
    }

    @Test
    @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다.")
    void 게시글_삭제() {
        // given
        Long articleId = 1L;
        Article article = createArticle();
        willDoNothing().given(articleRepository).deleteById(articleId);
        given(articleRepository.findById(articleId)).willReturn(Optional.ofNullable(article));

        // when
        articleService.deleteArticle(createUser(), articleId);

        // then
        then(articleRepository).should().deleteById(articleId);
        then(articleRepository).should().findById(articleId);
    }

    @Test
    @DisplayName("게시글의 수정 정보를 입력하면, 게시글을 수정한다.")
    void 게시글_수정() {
        // given
        Long articleId = 1L;
        Article article = createArticle();
        ArticleRequestDto dto = createArticleRequestDto("update", "update", "update");
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));
        given(articleRepository.save(article)).willReturn(article);

        // when
        articleService.updateArticle(createUser(), articleId, dto);

        // then
        assertThat(article)
                .hasFieldOrPropertyWithValue("comment", dto.getComment())
                .hasFieldOrPropertyWithValue("meetAt", dto.getMeetAt())
                .hasFieldOrPropertyWithValue("place", dto.getPlace());
        then(articleRepository).should().findById(articleId);
        then(articleRepository).should().save(article);
    }

    private Article createArticle() {
        return Article.builder()
                .id(1L)
                .user(createUser())
                .comment("test")
                .place("test")
                .meetAt("2022-12-12")
                .articleComments(List.of())
                .build();
    }

    private User createUser() {
        return User.builder()
                .email("test")
                .sex("male")
                .nickname("test")
                .authority(Authority.ROLE_USER)
                .birth(LocalDate.now())
                .major("test")
                .password("test")
                .build();
    }

    private ArticleRequestDto createArticleRequestDto(String comment, String meetAt, String place) {
        return ArticleRequestDto.builder()
                .comment(comment)
                .meetAt(meetAt)
                .place(place)
                .user(createUser())
                .build();
    }
}
