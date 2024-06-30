package com.metlushko.article.service;

import com.metlushko.article.entity.Article;
import com.metlushko.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class ArticleService {

    @Value("${article.statistics.days}")
    private int statisticsDays;

    private final ArticleRepository articleRepository;

    @Transactional
    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    public Page<Article> getAllArticles(int page, int size) {
        return articleRepository.findAll(PageRequest.of(page, size));
    }
    public long getNumberOfArtciclesByLastDays() {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(statisticsDays);
        return articleRepository.countByPublishDateBetween(sevenDaysAgo, today);
    }

}
