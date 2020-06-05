package com.villages.energyconsumption.Village;

public class VillageNotFoundException extends RuntimeException {
    public VillageNotFoundException(Integer id){
        super("Could not find the village with id " + id);
    }
}
