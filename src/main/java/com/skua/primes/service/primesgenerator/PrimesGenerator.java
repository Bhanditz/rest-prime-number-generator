package com.skua.primes.service.primesgenerator;

import java.util.List;

/**
 * Created by kunwar.singh on 09/02/2016.
 */
public interface PrimesGenerator {
    List<Long> generatePrimes(Long upperRange);

    enum PrimesGeneratorAlgo {
        FORK_JOIN, ITERATIVE, PARALLEL_STREAM, STREAM;
    }
}
