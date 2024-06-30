package com.metlushko.article.handler;


import com.metlushko.article.controller.ArticleController;
import com.metlushko.article.repository.ArticleRepository;
import com.metlushko.article.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = ArticleRepository.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void handleValidationExceptions() throws Exception {
        String requestBody = "{\"title\":\"\", \"author\":\"\", \"content\":\"\", \"publishDate\":\"2024-06-30\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title is mandatory"))
                .andExpect(jsonPath("$.author").value("Author is mandatory"))
                .andExpect(jsonPath("$.content").value("Content is mandatory"));
    }
}

