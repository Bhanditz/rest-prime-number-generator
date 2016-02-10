package com.skua.primes.api.controller;

import com.skua.primes.domain.PrimesResult;
import com.skua.primes.service.PrimesService;
import com.skua.primes.service.primesgenerator.PrimesGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/primes")
public class PrimesController {

    @Autowired
    private PrimesService primesService;

    @RequestMapping(value = "/algorithm/default/value/{upperLimit}", method = RequestMethod.GET)
    public ResponseEntity<PrimesResult> generatePrimesWithDefaultAlgorithm(@PathVariable("upperLimit") String upperLimit) {
        return new ResponseEntity<PrimesResult>(this.primesService.generatePrime(upperLimit, Optional.empty()), HttpStatus.OK);
    }

    @RequestMapping(value = "/algorithm/{algorithmName}/value/{upperLimit}", method = RequestMethod.GET)
    public ResponseEntity<PrimesResult> generatePrimes(@PathVariable("algorithmName") String algorithmName, @PathVariable("upperLimit") String upperLimit) {
        return new ResponseEntity<PrimesResult>(this.primesService.generatePrime(upperLimit, Optional.of(algorithmName.toUpperCase())), HttpStatus.OK);
    }

    @RequestMapping(value = "/find/{resultId}", method = RequestMethod.GET)
    public ResponseEntity<PrimesResult> getGeneratedPrimesById(@PathVariable("resultId") String resultId) {
        Optional<PrimesResult> primesResultOptional = this.primesService.getPrimesResult(resultId);
        PrimesResult primesResult = null;
        if (!primesResultOptional.isPresent()) {
            primesResult = new PrimesResult(null);
            primesResult.setErrorMessage("Result Not Found");
        } else {
            primesResult = primesResultOptional.get();
        }
        return new ResponseEntity<PrimesResult>(primesResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<List<PrimesResult>> getGeneratedPrimes() {
        return new ResponseEntity<List<PrimesResult>>(this.primesService.getPrimesResults(), HttpStatus.OK);
    }

    @RequestMapping(value = "/algorithmNames", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getPrimesAlgorithmNames() {
        return new ResponseEntity<List<String>>(
                Stream.of(PrimesGenerator.PrimesAlgorithm.values())
                        .map(PrimesGenerator.PrimesAlgorithm::name)
                        .collect(Collectors.toList())
                , HttpStatus.OK);
    }
}
