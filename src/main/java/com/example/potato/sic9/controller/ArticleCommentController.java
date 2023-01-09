package com.example.potato.sic9.controller;

import com.example.potato.sic9.annotation.AuthUser;
import com.example.potato.sic9.dto.articleComment.ArticleCommentRequestDto;
import com.example.potato.sic9.dto.articleComment.ArticleCommentResponseDto;
import com.example.potato.sic9.entity.User;
import com.example.potato.sic9.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public ResponseEntity<ArticleCommentResponseDto> postNewArticleComment(@AuthUser User user,
                                                                           @RequestBody ArticleCommentRequestDto dto) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(articleCommentService.saveArticleComment(user, dto));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteArticleComment(@AuthUser User user, @RequestParam Long id) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        articleCommentService.deleteArticleComment(user, id);
        return ResponseEntity.ok().build();
    }
}
