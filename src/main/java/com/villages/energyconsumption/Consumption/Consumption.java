package com.villages.energyconsumption.Consumption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.villages.energyconsumption.Counter.Counter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Consumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private Float amount;

    @JoinColumn(name = "counter_id", nullable = false)
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Counter counter;

    public Consumption(Float amount, LocalDateTime dateTime){
        this.amount = amount;
        this.dateTime = dateTime;
    }
    public Consumption() {
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Counter getCounter() {
        return counter;
    }

    public Integer getId() {
        return id;
    }
}
