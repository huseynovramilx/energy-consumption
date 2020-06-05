package com.villages.energyconsumption.Counter;

public class CounterDTO {
    private Integer id;
    private String villageName;

    public CounterDTO() {
    }

    public CounterDTO(Integer id, String villageName) {
        this.id = id;
        this.villageName = villageName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }
}

