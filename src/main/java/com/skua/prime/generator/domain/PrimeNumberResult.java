package com.skua.prime.generator.domain;

import java.util.ArrayList;
import java.util.List;

public class PrimeNumberResult {
    private final Long endRange;
    private final List<Long> primeNumbers;

    public PrimeNumberResult(Long limit) {
        this.endRange = limit;
        this.primeNumbers = new ArrayList<>();
    }

    public Long getEndRange() {
        return endRange;
    }

    public List<Long> getPrimeNumbers() {
        return primeNumbers;
    }

    public boolean addToResult(Long primeNumber) {
        return this.primeNumbers.add(primeNumber);
    }
}
