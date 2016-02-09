package com.skua.primes.service.primesgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

class ForkJoinPrimesGenerator extends RecursiveTask<List<Long>> implements PrimesGenerator {

    public static final long THRESHOLD = 10_000;

    private Long upperRange;
    private Long lowerRange;

    @Override
    public List<Long> generatePrimes(Long upperLimit) {
        ForkJoinPrimesGenerator forkJoinPrimesGenerator = new ForkJoinPrimesGenerator(2L, upperLimit);
        return forkJoinPrimesGenerator.compute();
    }

    ForkJoinPrimesGenerator() {
    }

    private ForkJoinPrimesGenerator(Long lowerRange, Long upperRange) {
        this.upperRange = upperRange;
        this.lowerRange = lowerRange;
    }

    @Override
    protected List<Long> compute() {
        Long length = upperRange - lowerRange;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        ForkJoinPrimesGenerator leftTask = new ForkJoinPrimesGenerator(lowerRange, lowerRange + length / 2);
        leftTask.fork();
        ForkJoinPrimesGenerator rightTask = new ForkJoinPrimesGenerator(lowerRange + length / 2, upperRange);
        List<Long> rightResult = rightTask.compute();
        List<Long> leftResult = leftTask.join();
        rightResult.addAll(leftResult);
        return rightResult;
    }

    private List<Long> computeSequentially() {
        final List<Long> primes = new ArrayList<>();
        LongStream
                .iterate(2L, i -> i + 1)
                .filter(i -> {
                    for (Long prime : primes)
                        if (i % prime == 0)
                            return false;
                    return true;
                })
                .limit(upperRange)
                .forEach(primes::add);
        return primes;
    }
}
