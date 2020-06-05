package com.villages.energyconsumption.Village;

import com.villages.energyconsumption.Counter.Counter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VillageController {
    private final VillageService villageService;

    public VillageController(VillageService villageService) {
        this.villageService = villageService;
    }

    @GetMapping("/villages")
    Iterable<Village> all(){
        return villageService.getAllVillages();
    }

    @PostMapping("/villages")
    Village addVillage(@RequestBody Village village){
        return villageService.addVillage(village);
    }

    @PostMapping("/villages/{id}")
    Counter addCounter(@PathVariable Integer id){
        return villageService.addCounter(id);
    }
}
