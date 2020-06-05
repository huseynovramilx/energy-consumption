package com.villages.energyconsumption.Consumption;

public class ConsumptionDTO {
    private Integer counter_id;
    private Float amount;

    public ConsumptionDTO() {
    }

    public Integer getCounter_id() {
        return counter_id;
    }

    public void setCounter_id(Integer counter_id) {
        this.counter_id = counter_id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public ConsumptionDTO(Float amount) {
        this.amount = amount;
    }
}
