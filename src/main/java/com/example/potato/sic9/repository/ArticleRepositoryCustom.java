package com.example.potato.sic9.repository;

import com.example.potato.sic9.entity.Article;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryCustom {
    List<Article> searchAllWithFilter(Long lastArticleId, List<String> places, List<String> sex, Pageable pageable);

    List<Article> searchAll(Long lastArticleId, Pageable pageable);
}
