package com.example.potato.sic9.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.potato.sic9.common.Authority;
import com.example.potato.sic9.config.TestConfig;
import com.example.potato.sic9.entity.Article;
import com.example.potato.sic9.entity.User;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestConfig.class)
class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach()
    void init() {
        User maleUser = createMaleUser();
        User femaleUser = createFemaleUser();
        List<User> users = Arrays.asList(maleUser, femaleUser);
        List<String> places = Arrays.asList("백록관", "천지관", "두리관", "석재");
        for (User user : users) {
            for (String place : places) {
                Article article = createArticle(user, place);
                articleRepository.save(article);
            }
        }
    }

    @AfterEach
    void teardown() {
        this.articleRepository.deleteAll();
        this.entityManager
                .createNativeQuery("ALTER TABLE article ALTER COLUMN `article_id` RESTART WITH 1")
                .executeUpdate();
    }

    @DisplayName("필터 테스트")
    @Nested
    class FilterCases {

        @Test
        @DisplayName("장소필터 1개, 성별필터 2개로 설정하고 제대로 조회되는지 확인")
        void 필터_백록관_남_여() {
            // given
            List<String> place = List.of("백록관");
            List<String> sex = Arrays.asList("male", "female");
            PageRequest pageRequest = PageRequest.of(0, 10, Direction.ASC, "createdAt");
            int maleCount = 0;
            int femaleCount = 0;

            // when
            List<Article> result = articleRepository.searchAllWithFilter(0L, place, sex, pageRequest);
            for (Article article : result) {
                if (Objects.equals(article.getUser().getSex(), "male")) {
                    maleCount++;
                } else {
                    femaleCount++;
                }
            }

            // then
            assertEquals(2, result.size());
            assertEquals(1, maleCount);
            assertEquals(1, femaleCount);
        }

        @Test
        @DisplayName("장소필터 2개, 성별필터 1개로 설정하고 제대로 조회되는지 확인")
        void 필터_백록관_천지관_남_여() {
            // given
            List<String> place = List.of("백록관", "천지관");
            List<String> sex = Arrays.asList("male", "female");
            PageRequest pageRequest = PageRequest.of(0, 10, Direction.ASC, "createdAt");
            int maleCount = 0;
            int femaleCount = 0;

            // when
            List<Article> result = articleRepository.searchAllWithFilter(0L, place, sex, pageRequest);
            for (Article article : result) {
                if (Objects.equals(article.getUser().getSex(), "male")) {
                    maleCount++;
                } else {
                    femaleCount++;
                }
            }

            // then
            assertEquals(4, result.size());
            assertEquals(2, maleCount);
            assertEquals(2, femaleCount);
        }

        @Test
        @DisplayName("장소필터 1개, 성별필터 1개로 설정하고 제대로 조회되는지 확인")
        void 필터_두리관_남() {
            // given
            List<String> place = List.of("두리관");
            List<String> sex = List.of("male");
            PageRequest pageRequest = PageRequest.of(0, 10, Direction.ASC, "createdAt");
            int maleCount = 0;
            int femaleCount = 0;

            // when
            List<Article> result = articleRepository.searchAllWithFilter(0L, place, sex, pageRequest);
            for (Article article : result) {
                if (Objects.equals(article.getUser().getSex(), "male")) {
                    maleCount++;
                } else {
                    femaleCount++;
                }
            }

            // then
            assertEquals(1, result.size());
            assertEquals(1, maleCount);
            assertEquals(0, femaleCount);
        }

        @Test
        @DisplayName("장소필터 3개, 성별필터 1개로 설정하고 제대로 조회되는지 확인")
        void 필터_백록관_두리관_석재_남() {
            // given
            List<String> place = List.of("백록관", "두리관", "석재");
            List<String> sex = List.of("female");
            PageRequest pageRequest = PageRequest.of(0, 10, Direction.ASC, "createdAt");
            int maleCount = 0;
            int femaleCount = 0;

            // when
            List<Article> result = articleRepository.searchAllWithFilter(0L, place, sex, pageRequest);
            for (Article article : result) {
                if (Objects.equals(article.getUser().getSex(), "male")) {
                    maleCount++;
                } else {
                    femaleCount++;
                }
            }

            // then
            assertEquals(3, result.size());
            assertEquals(0, maleCount);
            assertEquals(3, femaleCount);
        }

        @Test
        @DisplayName("장소필터 0개, 성별필터 0개로 설정하고 제대로 조회되는지 확인")
        void 필터_아무것도없이() {
            // given
            List<String> place = List.of();
            List<String> sex = List.of();
            PageRequest pageRequest = PageRequest.of(0, 10, Direction.ASC, "createdAt");
            int maleCount = 0;
            int femaleCount = 0;

            // when
            List<Article> result = articleRepository.searchAllWithFilter(0L, place, sex, pageRequest);
            for (Article article : result) {
                if (Objects.equals(article.getUser().getSex(), "male")) {
                    maleCount++;
                } else {
                    femaleCount++;
                }
            }
            
            // then
            assertEquals(0, result.size());
            assertEquals(0, maleCount);
            assertEquals(0, femaleCount);
        }
    }

    @Nested
    @DisplayName("페이징 테스트")
    class PagingCases {

        @Test
        @DisplayName("필터 없이 제대로 조회되는지 확인")
        void pagingWithoutFilter() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Direction.ASC, "createdAt"));

            // when
            List<Article> articles = articleRepository.searchAll(0L, pageRequest);
            Page<Article> pages = articleRepository.findAll(pageRequest);

            //then
            List<Article> contents = pages.getContent();
            assertEquals(3, articles.size());
            assertEquals(3, contents.size());
            assertEquals(8, pages.getTotalElements());
            assertEquals(0, pages.getNumber());
            assertEquals(3, pages.getTotalPages());
            assertEquals(articles, contents);
            assertTrue(pages.isFirst());
            assertTrue(pages.hasNext());
            assertFalse(pages.isLast());
            assertFalse(pages.hasPrevious());
            assertTrue(articles.get(0).getCreatedAt().isBefore(articles.get(articles.size() - 1).getCreatedAt()));
        }

        @Test
        @DisplayName("필터 있이 제대로 조회되는지 확인")
        void pagingWithFilter() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 4, Sort.by(Direction.ASC, "createdAt"));
            List<String> place = List.of("백록관", "천지관", "두리관", "석재");
            List<String> sex = List.of("male", "female");
            int maleCount = 0;
            int femaleCount = 0;

            // when
            List<Article> result = articleRepository.searchAllWithFilter(0L, place, sex, pageRequest);
            for (Article article : result) {
                if (Objects.equals(article.getUser().getSex(), "male")) {
                    maleCount++;
                } else {
                    femaleCount++;
                }
            }

            // then
            assertEquals(4, result.size());
            assertEquals(4, maleCount);
            assertEquals(0, femaleCount);
            assertTrue(result.get(0).getCreatedAt().isBefore(result.get(result.size() - 1).getCreatedAt()));
        }
    }

    @Nested
    @DisplayName("무한스크롤 테스트")
    class InfiniteScroll {

        @Test
        @DisplayName("최초로 필터 없이, createdAt 오름차순으로 제대로 조회되는지 확인")
        void firstWithoutFilterAndASC() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 4, Sort.by(Direction.ASC, "createdAt"));

            // when
            List<Article> result = articleRepository.searchAll(0L, pageRequest);

            // then
            assertEquals(4, result.size());
            assertEquals(1, result.get(0).getId());
            assertTrue(result.get(0).getCreatedAt().isBefore(result.get(result.size() - 1).getCreatedAt()));
        }

        @Test
        @DisplayName("최초로 필터 없이, createdAt 내림차순으로 제대로 조회되는지 확인")
        void firstWithoutFilterAndDESC() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 4, Sort.by(Direction.DESC, "createdAt"));

            // when
            List<Article> result = articleRepository.searchAll(99999999L, pageRequest);

            // then
            assertEquals(4, result.size());
            assertEquals(8, result.get(0).getId());
            assertTrue(result.get(0).getCreatedAt().isAfter(result.get(result.size() - 1).getCreatedAt()));
        }

        @Test
        @DisplayName("최초로 필터 있이, createdAt 오름차순으로 제대로 조회되는지 확인")
        void firstWithFilterAndASC() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 4, Sort.by(Direction.ASC, "createdAt"));
            List<String> place = List.of("백록관", "천지관");
            List<String> sex = List.of("male", "female");

            // when
            List<Article> result = articleRepository.searchAllWithFilter(0L, place, sex, pageRequest);

            // then
            assertEquals(4, result.size());
            assertTrue(result.get(0).getCreatedAt().isBefore(result.get(result.size() - 1).getCreatedAt()));
        }

        @Test
        @DisplayName("최초로 필터 있이, createdAt 내림차순으로 제대로 조회되는지 확인")
        void firstWithFilterAndDESC() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 4, Sort.by(Direction.DESC, "createdAt"));
            List<String> place = List.of("백록관", "천지관");
            List<String> sex = List.of("male", "female");

            // when
            List<Article> result = articleRepository.searchAllWithFilter(99999999L, place, sex, pageRequest);

            // then
            assertEquals(4, result.size());
            assertTrue(result.get(0).getCreatedAt().isAfter(result.get(result.size() - 1).getCreatedAt()));
        }

        @Test
        @DisplayName("최초 이후로 필터 없이, createdAt 오름차순으로 제대로 조회되는지 확인")
        void afterWithoutFilterAndASC() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 4, Sort.by(Direction.ASC, "createdAt"));

            // when
            List<Article> firstResult = articleRepository.searchAll(0L, pageRequest);
            List<Article> secondResult = articleRepository.searchAll(firstResult.get(firstResult.size() - 1).getId(),
                    pageRequest);

            // then
            assertEquals(4, firstResult.size());
            assertEquals(4, secondResult.size());
            assertEquals(5, secondResult.get(0).getId());
            assertTrue(
                    firstResult.get(0).getCreatedAt().isBefore(firstResult.get(firstResult.size() - 1).getCreatedAt()));
            assertTrue(secondResult.get(0).getCreatedAt()
                    .isBefore(secondResult.get(firstResult.size() - 1).getCreatedAt()));
        }

        @Test
        @DisplayName("최초 이후로 필터 없이, createdAt 내림차순으로 제대로 조회되는지 확인")
        void afterWithoutFilterAndDESC() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 4, Sort.by(Direction.DESC, "createdAt"));

            // when
            List<Article> firstResult = articleRepository.searchAll(99999999L, pageRequest);
            List<Article> secondResult = articleRepository.searchAll(firstResult.get(firstResult.size() - 1).getId(),
                    pageRequest);

            // then
            assertEquals(4, firstResult.size());
            assertEquals(4, secondResult.size());
            assertEquals(4, secondResult.get(0).getId());
            assertTrue(
                    firstResult.get(0).getCreatedAt().isAfter(firstResult.get(firstResult.size() - 1).getCreatedAt()));
            assertTrue(secondResult.get(0).getCreatedAt()
                    .isAfter(secondResult.get(firstResult.size() - 1).getCreatedAt()));
        }

        @Test
        @DisplayName("최초 이후로 필터 있이, createdAt 오름차순으로 제대로 조회되는지 확인")
        void afterWithFilterAndASC() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Direction.ASC, "createdAt"));
            List<String> place = List.of("백록관", "천지관");
            List<String> sex = List.of("male", "female");

            // when
            List<Article> firstResult = articleRepository.searchAllWithFilter(0L, place, sex, pageRequest);
            List<Article> secondResult = articleRepository.searchAllWithFilter(
                    firstResult.get(firstResult.size() - 1).getId(), place, sex,
                    pageRequest);

            // then
            assertEquals(2, firstResult.size());
            assertEquals(2, secondResult.size());
            assertTrue(secondResult.get(0).getId() > firstResult.get(firstResult.size() - 1).getId());
            assertTrue(
                    firstResult.get(0).getCreatedAt().isBefore(firstResult.get(firstResult.size() - 1).getCreatedAt()));
            assertTrue(secondResult.get(0).getCreatedAt()
                    .isBefore(secondResult.get(firstResult.size() - 1).getCreatedAt()));
        }

        @Test
        @DisplayName("최초 이후로 필터 있이, createdAt 내림차순으로 제대로 조회되는지 확인")
        void afterWithFilterAndDESC() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Direction.DESC, "createdAt"));
            List<String> place = List.of("백록관", "천지관");
            List<String> sex = List.of("male", "female");

            // when
            List<Article> firstResult = articleRepository.searchAllWithFilter(99999999L, place, sex, pageRequest);
            List<Article> secondResult = articleRepository.searchAllWithFilter(
                    firstResult.get(firstResult.size() - 1).getId(), place, sex,
                    pageRequest);

            // then
            assertEquals(2, firstResult.size());
            assertEquals(2, secondResult.size());
            assertTrue(secondResult.get(0).getId() < firstResult.get(firstResult.size() - 1).getId());
            assertTrue(
                    firstResult.get(0).getCreatedAt().isAfter(firstResult.get(firstResult.size() - 1).getCreatedAt()));
            assertTrue(secondResult.get(0).getCreatedAt()
                    .isAfter(secondResult.get(firstResult.size() - 1).getCreatedAt()));
        }

    }

    private User createMaleUser() {
        User user = User.builder()
                .sex("male")
                .birth(LocalDate.now())
                .nickname("test")
                .password("test")
                .major("test")
                .authority(Authority.ROLE_USER)
                .email("test")
                .build();
        return userRepository.save(user);
    }

    private User createFemaleUser() {
        User user = User.builder()
                .sex("female")
                .birth(LocalDate.now())
                .nickname("test")
                .password("test")
                .major("test")
                .authority(Authority.ROLE_USER)
                .email("test")
                .build();
        return userRepository.save(user);
    }

    private Article createArticle(User user, String place) {
        return Article.builder()
                .place(place)
                .comment("test")
                .meetAt("2022-12-12")
                .user(user)
                .build();
    }
}