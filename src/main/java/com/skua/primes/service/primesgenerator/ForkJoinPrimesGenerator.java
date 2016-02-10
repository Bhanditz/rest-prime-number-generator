package com.skua.primes.service.primesgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

class ForkJoinPrimesGenerator extends RecursiveTask<List<Long>> implements PrimesGenerator {

    public static final long THRESHOLD = 10_000;

    private final Long upperLimit;
    private final Long lowerLimit;

    @Override
    public List<Long> generatePrimes() {
        ForkJoinPrimesGenerator forkJoinPrimesGenerator = new ForkJoinPrimesGenerator(2L, upperLimit);
        return forkJoinPrimesGenerator.compute();
    }

    @Override
    public Long getUpperLimit() {
        return this.upperLimit;
    }

    ForkJoinPrimesGenerator(Long upperLimit) {
        this(2L, upperLimit);
    }

    private ForkJoinPrimesGenerator(Long lowerLimit, Long upperLimit) {
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
    }

    @Override
    protected List<Long> compute() {
        Long length = upperLimit - lowerLimit;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        ForkJoinPrimesGenerator leftTask = new ForkJoinPrimesGenerator(lowerLimit, lowerLimit + length / 2);
        leftTask.fork();
        ForkJoinPrimesGenerator rightTask = new ForkJoinPrimesGenerator(lowerLimit + length / 2, upperLimit);
        List<Long> rightResult = rightTask.compute();
        List<Long> leftResult = leftTask.join();
        rightResult.addAll(leftResult);
        return rightResult;
    }

    private List<Long> computeSequentially() {
        final List<Long> primes = new ArrayList<>();
        LongStream
                .iterate(lowerLimit, i -> i + 1)
                .filter(i -> {
                    for (Long prime : primes)
                        if (i % prime == 0)
                            return false;
                    return true;
                })
                .limit(upperLimit)
                .forEach(primes::add);
        return primes;
    }
}
