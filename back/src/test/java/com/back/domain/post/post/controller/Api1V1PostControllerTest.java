package com.back.domain.post.post.controller;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class Api1V1PostControllerTest {
  @Autowired
  private PostService postService;

  @Autowired
  private MockMvc mvc;

  @Test
  @DisplayName("다건 조회")
  void t1() throws Exception {
    // when

    // 1. API 호출
    var resultActions = mvc
        .perform(get("/api/v1/posts"))
        .andDo(print());

    // 2. 데이터 조회
    List<Post> posts = postService.getPosts();

    // then
    // 3. 응답 결과를 검증
    resultActions
        .andExpect(handler().handlerType(Api1V1PostController.class))
        .andExpect(handler().methodName("getItems"))
        .andExpect(status().isOk());

    // 4. 응답 결과를 반복
    for(int i = 0; i < posts.size(); i++) {
      Post post = posts.get(i);

      resultActions
          .andExpect(jsonPath("$[%d].id".formatted(i)).value(post.getId()))
          .andExpect(jsonPath("$[%d].title".formatted(i)).value(post.getTitle()));
    }
  }
}
