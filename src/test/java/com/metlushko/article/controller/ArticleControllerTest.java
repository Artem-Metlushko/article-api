package com.metlushko.article.controller;

import com.metlushko.article.dto.ArticleDto;
import com.metlushko.article.entity.Article;
import com.metlushko.article.mapper.ArticleMapper;
import com.metlushko.article.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private ArticleMapper articleMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Article article;
    private ArticleDto articleDTO;

    @BeforeEach
    void setUp() {
        article = new Article();
        article.setId(1L);
        article.setTitle("Title");
        article.setAuthor("Author");
        article.setContent("Content");
        article.setPublishDate(LocalDate.now());

        articleDTO = new ArticleDto();
        articleDTO.setTitle("Title");
        articleDTO.setAuthor("Author");
        articleDTO.setContent("Content");
        articleDTO.setPublishDate(LocalDate.now());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testCreateArticle() throws Exception {
        when(articleMapper.toArticle(any(ArticleDto.class))).thenReturn(article);
        when(articleService.saveArticle(any(Article.class))).thenReturn(article);

        mockMvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.author").value("Author"))
                .andExpect(jsonPath("$.content").value("Content"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetAllArticles() throws Exception {
        Page<Article> articlePage = new PageImpl<>(Collections.singletonList(article));
        Pageable pageable = PageRequest.of(0, 10);

        when(articleService.getAllArticles(0, 10)).thenReturn(articlePage);

        mockMvc.perform(get("/api/articles")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Title"))
                .andExpect(jsonPath("$.content[0].author").value("Author"))
                .andExpect(jsonPath("$.content[0].content").value("Content"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetArticleStats() throws Exception {
        when(articleService.getNumberOfArtciclesByLastDays()).thenReturn(5L);

        mockMvc.perform(get("/api/articles/statistics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void accessStatsEndpointWithAdminRole() throws Exception {
        mockMvc.perform(get("/api/articles/statistics"))
                .andExpect(status().isOk());
    }
}