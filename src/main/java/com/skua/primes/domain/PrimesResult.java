package com.skua.primes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.skua.primes.service.primesgenerator.PrimesGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PrimesResult {

    private final UUID resultId;
    private final Long upperLimit;
    private final List<Long> primes;
    private final PrimesGenerator.PrimesAlgorithm algorithm;
    @JsonIgnore
    private final LocalDateTime createTime;

    private String errorMessage;
    private Long processingTimeInNanoSeconds;

    public PrimesResult(Long upperLimit) {
        this(upperLimit, PrimesGenerator.PrimesAlgorithm.STREAM);
    }

    public PrimesResult(Long upperLimit, PrimesGenerator.PrimesAlgorithm algorithm) {
        this.resultId = UUID.randomUUID();
        this.upperLimit = upperLimit;
        this.primes = new ArrayList<>();
        this.algorithm = algorithm;
        createTime = LocalDateTime.now();
    }

    public Long getUpperLimit() {
        return upperLimit;
    }

    public List<Long> getPrimes() {
        return primes;
    }

    public boolean add(Long primeNumber) {
        return this.primes.add(primeNumber);
    }

    public boolean addAll(List<Long> primeNumbers) {
        return this.primes.addAll(primeNumbers);
    }

    public String getResultId() {
        return resultId.toString();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getProcessingTimeInNanoSeconds() {
        return processingTimeInNanoSeconds;
    }

    public void setProcessingTimeInNanoSeconds(Long processingTimeInNanoSeconds) {
        this.processingTimeInNanoSeconds = processingTimeInNanoSeconds;
    }

    public PrimesGenerator.PrimesAlgorithm getAlgorithm() {
        return algorithm;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("resultId", resultId)
                .add("upperLimit", upperLimit)
                .add("primes", primes)
                .add("algorithm", algorithm)
                .add("errorMessage", errorMessage)
                .add("processingTimeInNanoSeconds", processingTimeInNanoSeconds)
                .toString();
    }

    @Override
    public int hashCode() {
        return resultId.hashCode();
    }
}
