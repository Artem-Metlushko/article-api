package com.metlushko.article.controller;

import com.metlushko.article.dto.ArticleDto;
import com.metlushko.article.entity.Article;
import com.metlushko.article.mapper.ArticleMapper;
import com.metlushko.article.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    private final ArticleMapper articleMapper;

    @PostMapping
    public ResponseEntity<Article> createArticle(@Valid @RequestBody ArticleDto articleDTO) {
        Article article = articleMapper.toArticle(articleDTO);
        Article savedArticle = articleService.saveArticle(article);
        return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Article>> getAllArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Article> articles = articleService.getAllArticles(page, size);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/statistics")
    public ResponseEntity<Long> getStatistics() {
        long count = articleService.last7Days();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

}
