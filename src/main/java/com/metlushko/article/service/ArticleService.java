package com.metlushko.article.service;

import com.metlushko.article.entity.Article;
import com.metlushko.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    public Page<Article> getAllArticles(int page, int size) {
        return articleRepository.findAll(PageRequest.of(page, size));
    }
    public long last7Days() {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);
        return articleRepository.last7Days(sevenDaysAgo, today);
    }

}
