package com.skua.prime.generator.service.primesgenerator;

import java.util.List;

public interface PrimeNumberGenerator {
    List<Long> generatePrimes(Long primeNumberRange);
}
