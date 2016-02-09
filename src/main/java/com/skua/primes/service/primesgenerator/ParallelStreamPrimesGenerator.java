package com.skua.primes.service.primesgenerator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

class ParallelStreamPrimesGenerator implements PrimesGenerator {
    @Override
    public List<Long> generatePrimes(Long upperLimit) {
        return LongStream
                .rangeClosed(2L, upperLimit)
                .parallel()
                .filter(i -> isPrime(i))
                .boxed()
                .collect(Collectors.toList());
    }

    private boolean isPrime(long x) {
        return LongStream
                .rangeClosed(2L, (long) (Math.sqrt(x)))
                .parallel()
                .allMatch(n -> x % n != 0);
    }
}
