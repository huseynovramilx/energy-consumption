package com.villages.energyconsumption.Consumption;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class ConsumptionController {
    private final ConsumptionService consumptionService;

    public ConsumptionController(ConsumptionService consumptionService) {
        this.consumptionService = consumptionService;
    }

    @GetMapping("/consumption_report")
    Iterable<ConsumptionReport> getConsumptionReport(@RequestParam String duration){
        return consumptionService.getConsumptionReport(duration);
    }

    @PostMapping("/counter_callback")
    Consumption addConsumption(@RequestBody ConsumptionDTO consumptionDTO){
        return consumptionService.addConsumption(consumptionDTO.getCounter_id(), consumptionDTO.getAmount(), LocalDateTime.now());
    }
}
