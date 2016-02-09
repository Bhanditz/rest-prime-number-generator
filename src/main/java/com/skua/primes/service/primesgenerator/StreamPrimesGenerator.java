package com.skua.primes.service.primesgenerator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

class StreamPrimesGenerator implements PrimesGenerator {
    @Override
    public List<Long> generatePrimes(Long upperRange) {
        return LongStream
                .rangeClosed(2L, upperRange)
                .filter(i -> isPrime(i))
                .boxed()
                .collect(Collectors.toList());
    }

    private boolean isPrime(long x) {
        return LongStream
                .rangeClosed(2L, (long) (Math.sqrt(x)))
                .allMatch(n -> x % n != 0);
    }
}
