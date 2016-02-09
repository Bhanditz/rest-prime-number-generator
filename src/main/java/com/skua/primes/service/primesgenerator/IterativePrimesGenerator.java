package com.skua.primes.service.primesgenerator;

import java.util.ArrayList;
import java.util.List;

class IterativePrimesGenerator implements PrimesGenerator {
    @Override
    public List<Long> generatePrimes(Long upperLimit) {
        List<Long> primes = new ArrayList<>();
        for (long current = 2; current <= upperLimit; current++) {
            long sqrRoot = (long) Math.sqrt(current);
            boolean isPrime = true;
            for (long i = 2; i <= sqrRoot; i++) {
                if (current % i == 0) {
                    isPrime = false;
                }
            }
            if (isPrime) {
                primes.add(current);
            }
        }
        return primes;
    }
}
