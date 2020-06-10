package com.villages.energyconsumption.Consumption;

import com.villages.energyconsumption.Counter.Counter;
import com.villages.energyconsumption.Counter.CounterNotFoundException;
import com.villages.energyconsumption.Counter.CounterRepository;
import com.villages.energyconsumption.Village.Village;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("When calling Consumption Service")
class ConsumptionServiceTest {

    @MockBean
    private ConsumptionRepository consumptionRepository;

    @MockBean
    private CounterRepository counterRepository;

    @Autowired
    private ConsumptionService consumptionService;

    private static LocalDateTime dateTime;

    @BeforeAll
    static void setUp(){
        dateTime = LocalDateTime.now();
    }

    @Test
    @DisplayName("when getting consumption report")
    void getConsumptionReport() {
        ConsumptionReport[] consumptions = new ConsumptionReport[]{ new ConsumptionReport("Villa", 100200.00),
        new ConsumptionReport("Velli", 120000.20)};
        Iterable<ConsumptionReport> expectedConsumptionReports = Arrays.asList(consumptions);


        when(consumptionRepository.consumptionReport(any(LocalDateTime.class)))
                .thenReturn(expectedConsumptionReports);

        Iterable<ConsumptionReport> actualConsumptionReports = consumptionService.getConsumptionReport("24h");
        Assertions.assertIterableEquals(expectedConsumptionReports, actualConsumptionReports, "should return consumption report");
    }

    @Test
    @DisplayName("when adding consumption")
    void addConsumption() {
        Counter expectedCounter = new Counter(102, new Village("Villa"), dateTime);
        when(counterRepository.findById(any(Integer.class))).thenReturn(Optional.of(expectedCounter));

        Consumption expectedConsumption = new Consumption(10000.02f, dateTime);
        expectedConsumption.setCounter(expectedCounter);

        when(consumptionRepository.save(any(Consumption.class))).thenReturn(expectedConsumption);

        Consumption actualConsumption = consumptionService.addConsumption(102, 10000.02f, dateTime);
        Assertions.assertEquals(expectedConsumption.getAmount(), actualConsumption.getAmount(), "should return Consumption with expected amount");
        Assertions.assertEquals(expectedConsumption.getDateTime(), actualConsumption.getDateTime(), "should return Consumption with expected dateTime");
        Assertions.assertEquals(expectedConsumption.getCounter(), actualConsumption.getCounter(), "should return Consumption with expected Counter");
    }

    @Test
    @DisplayName("when adding consumption to non-existing counter")
    void addConsumptionNotFoundCounter() {

        when(counterRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        when(consumptionRepository.save(any(Consumption.class)))
                .thenReturn(new Consumption(10000.02f, dateTime));
        Assertions.assertThrows(CounterNotFoundException.class, ()-> consumptionService.addConsumption(102, 10000.02f, dateTime),
                "should have thrown CounterNotFoundException");
      }
}