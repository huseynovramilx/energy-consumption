package com.villages.energyconsumption.Counter;

import com.villages.energyconsumption.Consumption.Consumption;
import com.villages.energyconsumption.Consumption.ConsumptionDTO;
import com.villages.energyconsumption.Village.Village;
import com.villages.energyconsumption.Village.VillageService;
import org.springframework.web.bind.annotation.*;

@RestController
public class CounterController {
    private final CounterService counterService;

    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping("/counters")
    Iterable<Counter> all(){
        return counterService.getAllCounters();
    }

    @GetMapping("/counters/{id}")
    CounterDTO one(@PathVariable Integer id){
        Counter counter = counterService.getCounter(id);
        return new CounterDTO(counter.getId(), counter.getVillage().getName());
    }
    @PostMapping("/counters")
    CounterDTO addCounter(@RequestBody CounterInput counterInput){
        Counter counter = counterService.addCounter(counterInput.getId(), counterInput.getVillageId());
        return new CounterDTO(counter.getId(), counter.getVillage().getName());
    }
}
