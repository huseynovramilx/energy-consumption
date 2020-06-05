package com.villages.energyconsumption.Counter;

public class CounterNotFoundException extends RuntimeException {
    public CounterNotFoundException(Integer counterId) {
        super("Could not find the counter with id " + counterId);
    }
}
