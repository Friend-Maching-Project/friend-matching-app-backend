package com.example.potato.sic9.controller;

import com.example.potato.sic9.annotation.AuthUser;
import com.example.potato.sic9.dto.article.ArticleRequestDto;
import com.example.potato.sic9.dto.article.ArticleResponseDto;
import com.example.potato.sic9.entity.User;
import com.example.potato.sic9.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/article")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping()
    public ResponseEntity<Void> saveArticle(@AuthUser User user, @RequestBody ArticleRequestDto req) {
        articleService.saveArticle(req.of(user));
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<ArticleResponseDto>> getArticles() {
        return ResponseEntity.ok(articleService.getArticles());
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> getArticle(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleService.getArticle(articleId));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteArticle(@AuthUser User user, @RequestParam Long id) {
        articleService.deleteArticle(user, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@AuthUser User user, @PathVariable Long articleId, @RequestBody ArticleRequestDto dto) {
        return ResponseEntity.ok(articleService.updateArticle(user, articleId, dto));
    }

}
