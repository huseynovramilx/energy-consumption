package com.villages.energyconsumption.Village;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.villages.energyconsumption.Counter.Counter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("When calling Village Controller")
class VillageControllerTest {

    @MockBean
    private VillageService villageService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("when getting all villages")
    void all() throws Exception {
        Iterable<Village> villages = new ArrayList<>();
        when(villageService.getAllVillages()).thenReturn(villages);

        mockMvc.perform(get("/villages/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(villages));
    }

    @Test
    @DisplayName("when adding village")
    void addVillage() throws Exception {
       Village village = new Village("Villa");

        when(villageService.addVillage(any(Village.class))).thenReturn(village);

        mockMvc.perform(post("/villages/")
                .content(mapper.writeValueAsString(village))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(village.getId()))
                .andExpect(jsonPath("$.name").value(village.getName()));
    }

}