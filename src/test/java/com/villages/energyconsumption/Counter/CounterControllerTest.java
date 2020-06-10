package com.villages.energyconsumption.Counter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.villages.energyconsumption.Consumption.Consumption;
import com.villages.energyconsumption.Consumption.ConsumptionDTO;
import com.villages.energyconsumption.Consumption.ConsumptionReport;
import com.villages.energyconsumption.Village.Village;
import com.villages.energyconsumption.Village.VillageNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("When calling counterController")
class CounterControllerTest {

    @MockBean
    private CounterService counterService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    private static LocalDateTime dateTime;

    @BeforeAll
    static void setUp(){
        dateTime = LocalDateTime.now();
    }

    @Test
    @DisplayName("when getting all counters")
    void all() throws Exception{
        List<Counter> counters = new ArrayList<>();

        when(counterService.getAllCounters()).thenReturn(counters);

        mockMvc.perform(get("/counters")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(counters));
    }

    @Test
    @DisplayName("when getting one counter")
    void one() throws Exception {
        Integer id = 102;
        Counter counter = new Counter(102, new Village("Villa"), dateTime);

        when(counterService.getCounter(any(Integer.class))).thenReturn(counter);

        mockMvc.perform(get("/counters/102")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(counter.getId()))
                .andExpect(jsonPath("$.villageName").value(counter.getVillage().getName()));
    }

    @Test
    @DisplayName("when getting one counter with wrong id")
    void oneNotFound() throws Exception {
        when(counterService.getCounter(any(Integer.class))).thenThrow(new CounterNotFoundException(102));

        mockMvc.perform(get("/counters/102")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Could not find the counter with id 102"));
    }

    @Test
    @DisplayName("when adding counter")
    void addCounter() throws Exception {
        CounterInput counterInput = new CounterInput(102,2);

        Counter counter = new Counter(102, new Village("Villa"), dateTime);

        when(counterService.addCounter(any(Integer.class), any(Integer.class), any(LocalDateTime.class)))
                .thenReturn(counter);

        mockMvc.perform(post("/counters")
                .content(mapper.writeValueAsString(counterInput))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(counter.getId()))
                .andExpect(jsonPath("$.villageName").value(counter.getVillage().getName()));
    }
    @Test
    @DisplayName("when adding counter to not existing village")
    void addCounterNotFoundVillage() throws Exception {
        CounterInput counterInput = new CounterInput(102,2);

        when(counterService.addCounter(any(Integer.class), any(Integer.class), any(LocalDateTime.class)))
                .thenThrow(new VillageNotFoundException(2));

        mockMvc.perform(post("/counters")
                .content(mapper.writeValueAsString(counterInput))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Could not find the village with id 2"));
    }

}