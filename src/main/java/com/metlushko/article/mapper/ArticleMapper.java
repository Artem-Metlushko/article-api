package com.metlushko.article.mapper;

import com.metlushko.article.dto.ArticleDto;
import com.metlushko.article.entity.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    public Article toArticle(ArticleDto articleDto){
        return Article.builder()
                .id(null)
                .author(articleDto.getAuthor())
                .content(articleDto.getContent())
                .publishDate(articleDto.getPublishDate())
                .title(articleDto.getTitle())
                .build();
    }

}
