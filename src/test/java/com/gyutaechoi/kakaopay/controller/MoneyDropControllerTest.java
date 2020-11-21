package com.gyutaechoi.kakaopay.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyutaechoi.kakaopay.dto.MoneyDropRequest;
import com.gyutaechoi.kakaopay.service.MoneyDropService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URLDecoder;
import java.net.URLEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MoneyDropController.class)
class MoneyDropControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MoneyDropService moneyDropService;

    /**
     * @Valid 애노테이션이 제대로 동작하여 Exception Handler가 처리해주는지 테스트한다.
     * @throws Exception
     */
    @Test
    public void 돈뿌리기_API_0이하값_들어오면_예외_던진다() throws Exception {
        MoneyDropRequest req = new MoneyDropRequest(500, -5);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(req);

        mockMvc.perform(post("/money-drop")
                .contentType(MediaType.APPLICATION_JSON)
                .accept("application/json;charset=UTF-8")
                .header("X-USER-ID", "1")
                .header("X-ROOM-ID", "chatroom_id1")
                .content(jsonString))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("뿌릴 인원은 1명 이상이여야 합니다."));
    }

    /**
     * @MoneyShouldBeGreaterThanUsers 애노테이션이 제대로 동작하는지 테스트한다.
     * @throws Exception
     */
    @Test
    public void 뿌릴금액보다_인원수가_많으면_예외던진다() throws Exception {
        MoneyDropRequest req = new MoneyDropRequest(5, 7);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(req);

        mockMvc.perform(post("/money-drop")
                .contentType(MediaType.APPLICATION_JSON)
                .accept("application/json;charset=UTF-8")
                .header("X-USER-ID", "1")
                .header("X-ROOM-ID", "chatroom_id1")
                .content(jsonString))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("인원수 보다 많은 금액을 입력하세요."));
    }

    @Test
    public void urlDecodingTest() throws Exception{
        final String helloKorean = "안녕";
        final String encodedHelloKorean = "%EC%95%88%EB%85%95";
        assertThat(URLEncoder.encode(helloKorean, "UTF-8"), is(encodedHelloKorean));
        assertThat(URLDecoder.decode(encodedHelloKorean, "UTF-8"), is(helloKorean));

        final String hello = "hello12__A";
        assertThat(URLEncoder.encode(hello, "UTF-8"), is(hello));
    }

}