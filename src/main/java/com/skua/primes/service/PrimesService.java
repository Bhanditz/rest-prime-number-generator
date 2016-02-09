package com.skua.primes.service;

import com.skua.primes.domain.PrimesResult;
import com.skua.primes.service.primesgenerator.PrimesGenerator;

import java.util.Optional;

public interface PrimesService {
    PrimesResult generatePrime(String upperLimit, Optional<String> algorithm);
}
