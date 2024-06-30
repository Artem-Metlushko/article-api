package com.metlushko.article.service;

import com.metlushko.article.entity.Article;
import com.metlushko.article.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;


    @Test
    void testSaveArticle() {
        Article article = new Article();
        article.setTitle("Title");
        article.setAuthor("Author");
        article.setContent("Content");
        article.setPublishDate(LocalDate.now());

        when(articleRepository.save(any(Article.class))).thenReturn(article);

        Article savedArticle = articleService.saveArticle(article);

        assertNotNull(savedArticle);
        assertEquals("Title", savedArticle.getTitle());
        verify(articleRepository, times(1)).save(article);
    }

    @Test
    void testGetAllArticles() {
        Article article = new Article();
        article.setTitle("Title");
        article.setAuthor("Author");
        article.setContent("Content");
        article.setPublishDate(LocalDate.now());

        List<Article> articles = Collections.singletonList(article);
        Page<Article> articlePage = new PageImpl<>(articles);

        Pageable pageable = PageRequest.of(0, 10);
        when(articleRepository.findAll(pageable)).thenReturn(articlePage);

        Page<Article> result = articleService.getAllArticles(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Title", result.getContent().get(0).getTitle());
        verify(articleRepository, times(1)).findAll(pageable);
    }

    @Test
    void testLast7Days() {
        LocalDate today = LocalDate.of(2024, 6, 30);
        LocalDate sevenDaysAgo = today.minusDays(7);
        long expectedCount = 5L;

        when(articleRepository.countByPublishDateBetween(sevenDaysAgo, today)).thenReturn(expectedCount);
        ArticleService articleService = new ArticleService(articleRepository);

        long actualCount = articleService.getNumberOfArtciclesByLastDays();

        assertEquals(expectedCount, actualCount);
    }




}