package com.metlushko.article.repository;

import com.metlushko.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, PagingAndSortingRepository<Article, Long> {
    long last7Days(LocalDate startDate, LocalDate endDate);
}
