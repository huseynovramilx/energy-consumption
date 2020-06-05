package com.villages.energyconsumption.Counter;

import com.villages.energyconsumption.Consumption.Consumption;
import com.villages.energyconsumption.Consumption.ConsumptionRepository;
import com.villages.energyconsumption.Village.Village;
import com.villages.energyconsumption.Village.VillageNotFoundException;
import com.villages.energyconsumption.Village.VillageRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CounterService {

    private final CounterRepository counterRepository;
    private final VillageRepository villageRepository;
    private final ConsumptionRepository consumptionRepository;

    public CounterService(CounterRepository counterRepository, VillageRepository villageRepository, ConsumptionRepository consumptionRepository) {
        this.counterRepository = counterRepository;
        this.villageRepository = villageRepository;
        this.consumptionRepository = consumptionRepository;
    }

    public Iterable<Counter> getAllCounters(){
        return counterRepository.findAll();
    }

    public Counter getCounter(Integer id){
        Optional<Counter> optionalCounter = counterRepository.findById(id);
        if(optionalCounter.isEmpty()){
            throw new CounterNotFoundException(id);
        }
        return optionalCounter.get();
    }

    public Consumption addConsumption(Integer counterId, Float amount){
        Optional<Counter> optionalCounter = counterRepository.findById(counterId);
        if(optionalCounter.isEmpty()){
            throw new CounterNotFoundException(counterId);
        }
        Counter counter = optionalCounter.get();
        Consumption consumption = new Consumption(amount);
        counterRepository.save(counter);
        return consumption;
    }

    public Counter addCounter(Integer counterId, Integer villageId) {
        Optional<Village> optionalVillage = villageRepository.findById(villageId);
        if(optionalVillage.isEmpty()){
            throw new VillageNotFoundException(villageId);
        }
        Counter counter = new Counter(counterId, optionalVillage.get());
        counterRepository.saveAndFlush(counter);
        consumptionRepository.save(counter.getConsumptionList().get(0));
        return counter;
    }
}
