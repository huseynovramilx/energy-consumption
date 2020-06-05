package com.villages.energyconsumption.Counter;

public class CounterInput{
    private Integer id;
    private Integer villageId;

    public CounterInput() {
    }

    public CounterInput(Integer id, Integer villageId) {
        this.id = id;
        this.villageId = villageId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }
}
