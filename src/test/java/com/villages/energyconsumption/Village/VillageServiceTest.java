package com.villages.energyconsumption.Village;

import com.villages.energyconsumption.Counter.Counter;
import com.villages.energyconsumption.Counter.CounterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("When calling Village Service")
class VillageServiceTest {

    @MockBean
    private VillageRepository villageRepository;

    @Autowired
    private VillageService villageService;

    private static LocalDateTime dateTime;

    @BeforeAll
    static void setUp(){
        dateTime = LocalDateTime.now();
    }

    @Test
    @DisplayName("when getting all villages")
    void getAllVillages() {
        Village[] villages = new Village[]{ new Village("Villa"),
                 new Village("Venna")};

        List<Village> expectedVillages = Arrays.asList(villages);


        when(villageRepository.findAll())
                .thenReturn(expectedVillages);

        Iterable<Village> actualVillages = villageService.getAllVillages();
        Assertions.assertIterableEquals(expectedVillages, actualVillages, "should return expected Villages List");
    }

    @Test
    @DisplayName("when adding village")
    void addVillage() {
        Village expectedVillage = new Village("Villa");
        when(villageRepository.save(any(Village.class))).thenReturn(expectedVillage);
        Village actualVillage = villageService.addVillage(expectedVillage);
        Assertions.assertEquals(expectedVillage,actualVillage, "should return expected Village");
    }
}