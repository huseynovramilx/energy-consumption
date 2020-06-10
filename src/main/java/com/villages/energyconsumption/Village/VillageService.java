package com.villages.energyconsumption.Village;

import com.villages.energyconsumption.Counter.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class VillageService {

    private final VillageRepository villageRepository;

    public VillageService(VillageRepository villageRepository) {
        this.villageRepository = villageRepository;
    }

    public Iterable<Village> getAllVillages(){
        return villageRepository.findAll();
    }

    public Village addVillage(Village village){
        villageRepository.save(village);
        return village;
    }

}
