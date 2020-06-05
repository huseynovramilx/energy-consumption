package com.villages.energyconsumption.Consumption;

public class ConsumptionReport {
    private String villageName;
    private Double consumption;

    public ConsumptionReport() {
    }

    public ConsumptionReport(String villageName, Double consumption) {
        this.villageName = villageName;
        this.consumption = consumption;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }
}
