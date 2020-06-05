package com.villages.energyconsumption.Village;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.villages.energyconsumption.Consumption.Consumption;
import com.villages.energyconsumption.Counter.Counter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Village {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @JsonManagedReference
    @org.hibernate.annotations.Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToMany(mappedBy = "village", orphanRemoval =true)
    private List<Counter> counters = new ArrayList<Counter>();

    public Village(){
    }

    public void addCounter(Counter counter){
        counters.add(counter);
        counter.setVillage(this);
    }

    public void removeCounter(Counter counter){
        counters.remove(counter);
        counter.setVillage(null);
    }

    public Village(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }


}
