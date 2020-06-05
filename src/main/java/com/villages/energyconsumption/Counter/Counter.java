package com.villages.energyconsumption.Counter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.villages.energyconsumption.Consumption.Consumption;
import com.villages.energyconsumption.Village.Village;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Counter {
    @Id
    private Integer id;

    @JsonManagedReference
    @org.hibernate.annotations.Cascade(value = CascadeType.SAVE_UPDATE)
    @OneToMany(mappedBy = "counter",orphanRemoval =true)
    private List<Consumption> consumptionList = new ArrayList<Consumption>();

    @JoinColumn(name = "village_id", nullable = false)
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Village village;

    public void addConsumption(Consumption consumption){
        consumptionList.add(consumption);
        consumption.setCounter(this);
    }

    public void removeConsumption(Consumption consumption){
        consumptionList.remove(consumption);
        consumption.setCounter(null);
    }

    public List<Consumption> getConsumptionList() {
        return consumptionList;
    }

    public Counter(Integer id, Village village) {
        this.id = id;
        this.village = village;
        this.addConsumption(new Consumption(0.0f));
    }

    public Counter(){
        this.addConsumption(new Consumption(0.0f));
    }

    public Village getVillage() {
        return village;
    }

    public void setVillage(Village village) {
        this.village = village;
    }

    public Integer getId() {
        return id;
    }

}
