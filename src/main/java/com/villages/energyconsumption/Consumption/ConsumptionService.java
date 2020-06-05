package com.villages.energyconsumption.Consumption;

import com.villages.energyconsumption.Counter.Counter;
import com.villages.energyconsumption.Counter.CounterNotFoundException;
import com.villages.energyconsumption.Counter.CounterRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class ConsumptionService {
    private final ConsumptionRepository consumptionRepository;
    private final CounterRepository counterRepository;

    public ConsumptionService(ConsumptionRepository consumptionRepository, CounterRepository counterRepository) {
        this.consumptionRepository = consumptionRepository;
        this.counterRepository = counterRepository;
    }

    public Iterable<ConsumptionReport> getConsumptionReport(String duration){
        duration = duration.substring(0,duration.length()-1);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTime = now.minusHours(Integer.parseInt(duration));
        return consumptionRepository.consumptionReport(dateTime);
    }

    public Consumption addConsumption(Integer counter_id, Float amount) {
        Optional<Counter> optionalCounter = counterRepository.findById(counter_id);
        if(optionalCounter.isEmpty()){
            throw new CounterNotFoundException(counter_id);
        }
        Consumption consumption = new Consumption(amount);
        consumption.setCounter(optionalCounter.get());
        consumptionRepository.save(consumption);
        return consumption;
    }
}
