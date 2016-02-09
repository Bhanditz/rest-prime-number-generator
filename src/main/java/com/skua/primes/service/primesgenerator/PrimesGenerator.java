package com.skua.primes.service.primesgenerator;

import java.util.List;

public interface PrimesGenerator {
    List<Long> generatePrimes(Long upperLimit);

    enum PrimesAlgorithm {
        FORK_JOIN, ITERATIVE, PARALLEL_STREAM, STREAM;
    }
}
