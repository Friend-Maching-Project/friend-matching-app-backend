package com.example.potato.sic9.repository;

import com.example.potato.sic9.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
