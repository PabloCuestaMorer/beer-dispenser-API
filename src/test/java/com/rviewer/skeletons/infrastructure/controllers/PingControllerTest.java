package com.rviewer.skeletons.infrastructure.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getPing_success() throws Exception {
        mockMvc
                .perform(get("/ping"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPing_returnsPong() throws Exception {
        mockMvc
                .perform(get("/ping"))
                .andExpect(content().string(containsString("pong")));
    }
}
