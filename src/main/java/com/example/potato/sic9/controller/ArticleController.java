package com.example.potato.sic9.controller;

import com.example.potato.sic9.annotation.AuthUser;
import com.example.potato.sic9.dto.article.ArticleRequestDto;
import com.example.potato.sic9.dto.article.ArticleResponseDto;
import com.example.potato.sic9.entity.User;
import com.example.potato.sic9.service.ArticleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/article")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping()
    public ResponseEntity<Void> saveArticle(@AuthUser User user, @RequestBody ArticleRequestDto req) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        articleService.saveArticle(req.of(user));
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<ArticleResponseDto>> getArticles(@RequestParam Long lastArticleId,
                                                                Pageable pageable) {
        return ResponseEntity.ok(articleService.getArticles(lastArticleId, pageable));
    }

    // TODO : 필터 dto로 리펙토링 해보기
    @GetMapping("/filter")
    public ResponseEntity<List<ArticleResponseDto>> getArticlesWithFilter(
            @RequestParam Long lastArticleId,
            @RequestParam(value = "place", required = false, defaultValue = "백록관, 천지관, 두리관, 석재") List<String> places,
            @RequestParam(value = "sex", required = false, defaultValue = "male, female") List<String> sex,
            Pageable pageable) {
        return ResponseEntity.ok(articleService.getArticlesWithFilters(lastArticleId, places, sex, pageable));
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> getArticle(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleService.getArticle(articleId));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteArticle(@AuthUser User user, @RequestParam Long id) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        articleService.deleteArticle(user, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@AuthUser User user, @PathVariable Long articleId,
                                                            @RequestBody ArticleRequestDto dto) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(articleService.updateArticle(user, articleId, dto));
    }

}
