package com.rviewer.skeletons.infrastructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rviewer.skeletons.domain.requests.CreateDispenserRequest;
import com.rviewer.skeletons.domain.requests.UpdateDispenserStatusRequest;
import com.rviewer.skeletons.domain.responses.CreateDispenserResponse;
import com.rviewer.skeletons.domain.responses.DispenserSpendingResponse;
import com.rviewer.skeletons.domain.services.DispenserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DispenserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DispenserService dispenserService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        // Set up mock responses for the dispenser service
        CreateDispenserResponse createResponse = new CreateDispenserResponse("123", 0.0653);
        when(dispenserService.createDispenser(any(CreateDispenserRequest.class))).thenReturn(createResponse);

        when(dispenserService.updateStatus(any(Long.class), any(UpdateDispenserStatusRequest.class)))
                .thenReturn((ResponseEntity) ResponseEntity.accepted().body("Status of the tap changed correctly"));

        DispenserSpendingResponse spendingResponse = new DispenserSpendingResponse(100.0, null);
        when(dispenserService.calculateSpending(any(Long.class))).thenReturn(spendingResponse);
    }

    @Test
    public void createDispenser_success() throws Exception {
        CreateDispenserRequest request = new CreateDispenserRequest();
        request.setFlowVolume(0.0653);

        mockMvc
                .perform(post("/dispenser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.flowVolume").value(0.0653));
    }

    @Test
    public void updateDispenserStatus_success() throws Exception {
        UpdateDispenserStatusRequest request = new UpdateDispenserStatusRequest();
        request.setStatus("open");
        request.setUpdatedAt(LocalDateTime.now());

        mockMvc.perform(put("/dispenser/123/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Status of the tap changed correctly"));
    }

    @Test
    public void getSpending_success() throws Exception {
        mockMvc
                .perform(get("/dispenser/123/spending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalSpent").value(100.0));
    }
}
