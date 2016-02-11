package com.skua.primes.service;

import com.skua.primes.domain.PrimesResult;
import com.skua.primes.service.primesgenerator.PrimesGeneratorHarness;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class PrimesServiceImplTest {

    @Mock
    private PrimesGeneratorHarness primesGeneratorHarness;

    @InjectMocks
    private PrimesService primesService = new PrimesServiceImpl();

    @Test
    public void testGetPrimesResult() throws Exception {
        doNothing().when(primesGeneratorHarness).queueForProcessing(null);
        PrimesResult primesResult = this.primesService.generatePrime("100", Optional.of("STREAM"));
        Optional<PrimesResult> primesResultOptional = this.primesService.getPrimesResult(primesResult.getResultId());
        assertTrue("Prime Result must be present", primesResultOptional.isPresent());
        assertTrue("Prime Result Request Id must match", primesResultOptional.get().getResultId().equals(primesResult.getResultId()));
    }

    @Test
    public void testGetPrimesResults() throws Exception {
        doNothing().when(primesGeneratorHarness).queueForProcessing(null);
        PrimesResult primesResult = this.primesService.generatePrime("100", Optional.of("STREAM"));
        List<PrimesResult> primesResults = this.primesService.getPrimesResults();
        assertTrue("Primes Result List must contain successful Requests", primesResults.contains(primesResult));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGeneratePrimeOnNegativeInput() throws Exception {
        doNothing().when(primesGeneratorHarness).queueForProcessing(null);
        this.primesService.generatePrime("-1", Optional.empty());
    }

    @Test(expected = NumberFormatException.class)
    public void testGeneratePrimeOnBreachOfUpperBound() throws Exception {
        doNothing().when(primesGeneratorHarness).queueForProcessing(null);
        this.primesService.generatePrime("100000000000000000000000000000000000000000000", Optional.empty());
    }

    @Test(expected = NumberFormatException.class)
    public void testGeneratePrimeOnInvalidCharacterInput() throws Exception {
        doNothing().when(primesGeneratorHarness).queueForProcessing(null);
        this.primesService.generatePrime("1+w", Optional.empty());
    }

    @Test(expected = NumberFormatException.class)
    public void testGeneratePrimeOnEmptyInput() throws Exception {
        doNothing().when(primesGeneratorHarness).queueForProcessing(null);
        this.primesService.generatePrime("", Optional.empty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGeneratePrimeOnInvalidAlgorithm() throws Exception {
        doNothing().when(primesGeneratorHarness).queueForProcessing(null);
        this.primesService.generatePrime("2", Optional.of("DOES_NOT_EXIST"));
    }
}