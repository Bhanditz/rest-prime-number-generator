package com.skua.primes.service.primesgenerator;

import java.util.List;

public class PrimesGeneratorFactory {

    public List<Long> generatePrimes(PrimesGenerator.PrimesGeneratorAlgo algo, Long upperRange) {
        PrimesGenerator primesGenerator = null;
        switch (algo) {
            case FORK_JOIN:
                primesGenerator = new ForkJoinPrimesGenerator();
                break;
            case ITERATIVE:
                primesGenerator = new IterativePrimesGenerator();
                break;
            case STREAM:
                primesGenerator = new StreamPrimesGenerator();
                break;
            case PARALLEL_STREAM:
                primesGenerator = new ParallelStreamPrimesGenerator();
                break;
        }
        return primesGenerator.generatePrimes(upperRange);
    }
}
