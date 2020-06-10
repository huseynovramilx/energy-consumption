package com.villages.energyconsumption.Counter;

import com.villages.energyconsumption.Village.Village;
import com.villages.energyconsumption.Village.VillageNotFoundException;
import com.villages.energyconsumption.Village.VillageRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("When calling Counter Service")
class CounterServiceTest {

    @MockBean
    private CounterRepository counterRepository;

    @MockBean
    private VillageRepository villageRepository;

    @Autowired
    private CounterService counterService;

    private static LocalDateTime dateTime;

    @BeforeAll
    static void setUp(){
        dateTime = LocalDateTime.now();
    }

    @Test
    @DisplayName("when getting all counters")
    void getAllCounters() throws Exception {
        Counter[] counters = new Counter[]{ new Counter(102, new Village("Villa"), dateTime),
                new Counter(103, new Village("Venna"), dateTime)};
        List<Counter> expectedCounters = Arrays.asList(counters);


        when(counterRepository.findAll())
                .thenReturn(expectedCounters);

        Iterable<Counter> actualCounters = counterService.getAllCounters();
        Assertions.assertIterableEquals(expectedCounters, actualCounters, "should return expected counters list");
    }

    @Test
    @DisplayName("when getting counter")
    void getCounter() throws Exception{
        Counter expectedCounter = new Counter(102, new Village("Villa"), dateTime);

        when(counterRepository.findById(any(Integer.class))).thenReturn(Optional.of(expectedCounter));

        Counter actualCounter = counterService.getCounter(102);
        Assertions.assertEquals(expectedCounter, actualCounter, "should return expected counter");
    }

    @Test
    @DisplayName("when getting counter with wrong id")
    void getCounterNotFound() throws Exception{
        when(counterRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(CounterNotFoundException.class, ()->counterService.getCounter(102),"should have thrown CounterNotFoundException");
    }

    @Test
    @DisplayName("when adding counter")
    void addCounter() throws Exception {
        Village expectedVillage = new Village("Villa");

        when(villageRepository.findById(any(Integer.class))).thenReturn(Optional.of(expectedVillage));
        Counter expectedCounter = new Counter(102, expectedVillage, dateTime);
        Counter actualCounter = counterService.addCounter(102, 1, dateTime);
        Assertions.assertEquals(expectedCounter.getId(), actualCounter.getId(),"should return counter with expected id");
        Assertions.assertEquals(expectedCounter.getVillage(), actualCounter.getVillage(), "should return counter with expected village");
    }

    @Test
    @DisplayName("when adding counter to non-existing village")
    void addCounterNotFound() throws Exception {
        when(villageRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(VillageNotFoundException.class,
                ()-> counterService.addCounter(102, 1, dateTime), "should have thrown VillageNotFoundException");
     }
}