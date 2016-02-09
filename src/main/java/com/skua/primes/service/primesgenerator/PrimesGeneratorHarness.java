package com.skua.primes.service.primesgenerator;

import com.skua.primes.domain.PrimesResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrimesGeneratorHarness {

    public List<Long> generatePrimes(Long upperLimit, PrimesGenerator.PrimesAlgorithm algorithm) {
        PrimesGenerator primesGenerator = null;
        switch (algorithm) {
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
        return primesGenerator.generatePrimes(upperLimit);
    }

    public void queueForProcessing(PrimesResult primesResult) {

    }
}
