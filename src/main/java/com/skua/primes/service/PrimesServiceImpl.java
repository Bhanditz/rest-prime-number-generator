package com.skua.primes.service;

import com.skua.primes.domain.PrimesResult;
import com.skua.primes.service.primesgenerator.PrimesGenerator;
import com.skua.primes.service.primesgenerator.PrimesGeneratorHarness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrimesServiceImpl implements PrimesService {

    Map<String, PrimesResult> resultCache = new HashMap<>();

    @Autowired
    private PrimesGeneratorHarness primesGeneratorHarness;

    @Override
    public List<PrimesResult> getPrimesResults() {
        return this.resultCache.values()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PrimesResult> getPrimesResult(final String resultId) {
        return this.resultCache.values()
                .stream()
                .filter(r -> r.getResultId().equals(resultId))
                .findAny();
    }

    @Override
    public PrimesResult generatePrime(String upperLimit, Optional<String> algorithm) {
        PrimesResult primesResult = validatePrimesInput(upperLimit, algorithm);
        this.primesGeneratorHarness.queueForProcessing(primesResult);
        resultCache.put(primesResult.getResultId(), primesResult);
        return primesResult;
    }

    /*
    * Remove objects which are older than given interval
    * */
    @Override
    public void pruneCacheOlderThanInterval(long intervalInMinutes) {
        resultCache.values()
                .stream()
                .filter(p -> p.getCreateTime().compareTo(LocalDateTime.now().minusMinutes(intervalInMinutes)) > 0)
                .map(PrimesResult::getResultId)
                .forEach(k -> resultCache.remove(k));
    }

    private PrimesResult validatePrimesInput(String upperLimit, Optional<String> algorithm) {
        Long primeNumber = Long.parseLong(upperLimit);
        if (primeNumber <= 0) {
            throw new IllegalArgumentException("Input Number must be greater than 0");
        }
        if (algorithm.isPresent()) {
            PrimesGenerator.PrimesAlgorithm primesAlgorithm = PrimesGenerator.PrimesAlgorithm.valueOf(algorithm.get());
            return new PrimesResult(primeNumber, primesAlgorithm);
        } else {
            return new PrimesResult(primeNumber);
        }
    }
}
