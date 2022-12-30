package com.example.potato.sic9.repository;

import com.example.potato.sic9.entity.Article;
import com.example.potato.sic9.entity.QArticle;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Article> searchAllWithFilter(Long lastArticleId, List<String> places, List<String> sex,
                                             Pageable pageable) {
        QArticle qArticle = QArticle.article;
        return jpaQueryFactory
                .selectFrom(qArticle)
                .where(qArticle.place.in(places),
                        qArticle.user.sex.in(sex),
                        getDirection(pageable) == Order.ASC ? qArticle.id.gt(lastArticleId)
                                : qArticle.id.lt(lastArticleId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(articleSort(pageable))
                .fetch();
    }

    @Override
    public List<Article> searchAll(Long lastArticleId, Pageable pageable) {
        QArticle qArticle = QArticle.article;
        return jpaQueryFactory
                .selectFrom(qArticle)
                .where(getDirection(pageable) == Order.ASC ? qArticle.id.gt(lastArticleId)
                        : qArticle.id.lt(lastArticleId))
                .limit(pageable.getPageSize())
                .orderBy(articleSort(pageable))
                .fetch();
    }

    private OrderSpecifier<?> articleSort(Pageable page) {
        QArticle qArticle = QArticle.article;
        if (!page.getSort().isEmpty()) {
            for (Sort.Order order : page.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "createdAt":
                        return new OrderSpecifier<>(direction, qArticle.createdAt);
                }
            }
        }
        return null;
    }

    private Order getDirection(Pageable pageable) {
        Order direction = null;
        for (Sort.Order order : pageable.getSort()) {
            direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
        }
        return direction;
    }
}
