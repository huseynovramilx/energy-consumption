package com.villages.energyconsumption.Consumption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, Integer> {

    @Query("select new com.villages.energyconsumption.Consumption.ConsumptionReport(co.village.name, sum(c.amount)) from Consumption c join c.counter co where c.dateTime>:dateTime group by co.village.id")
    public Iterable<ConsumptionReport> consumptionReport(@Param("dateTime") LocalDateTime dateTime);
}
