package com.villages.energyconsumption.Consumption;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.villages.energyconsumption.Counter.CounterNotFoundException;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("When calling consumption controller")
class ConsumptionControllerTest {

    @MockBean
    private ConsumptionService consumptionService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private static LocalDateTime dateTime;

    @BeforeAll
    static void setUp(){
        dateTime = LocalDateTime.now();
    }

    @DisplayName("when getting consumption report")
    @Test
    void getConsumptionReport() throws Exception {
        String duration = "24h";
        ConsumptionReport[] consumptionReports = new ConsumptionReport[]{
                new ConsumptionReport("Villa", 10000.20),
                new ConsumptionReport("Venna", 1000.25)
        };
        List<ConsumptionReport> expectedConsumptionReports = Arrays.asList(consumptionReports);
        when(consumptionService.getConsumptionReport(any(String.class))).thenReturn(expectedConsumptionReports);

        mockMvc.perform(get("/consumption_report")
                .param("duration", duration)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("when adding consumption")
    void addConsumption() throws Exception {
        ConsumptionDTO consumptionDTO = new ConsumptionDTO(5.98f);
        consumptionDTO.setCounter_id(1);
        Consumption consumption = new Consumption(5.98f, dateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        when(consumptionService.addConsumption(any(Integer.class), any(Float.class), any(LocalDateTime.class)))
                .thenReturn(consumption);

        mockMvc.perform(post("/counter_callback")
                .content(mapper.writeValueAsString(consumptionDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(consumption.getAmount()))
                .andExpect(jsonPath("$.dateTime").value(containsString(formatter.format(consumption.getDateTime()))));
    }

    @Test
    @DisplayName("when adding consumption to non-existing counter")
    void addConsumptionNotFoundCounter() throws Exception {
        ConsumptionDTO consumptionDTO = new ConsumptionDTO(5.98f);
        consumptionDTO.setCounter_id(1);

        when(consumptionService.addConsumption(any(Integer.class), any(Float.class), any(LocalDateTime.class)))
                .thenThrow(new CounterNotFoundException(1));

        mockMvc.perform(post("/counter_callback")
                .content(mapper.writeValueAsString(consumptionDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Could not find the counter with id 1"));
    }
}