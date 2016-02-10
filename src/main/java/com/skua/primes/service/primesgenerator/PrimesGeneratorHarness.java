package com.skua.primes.service.primesgenerator;

import com.skua.primes.domain.PrimesResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class PrimesGeneratorHarness {
    private static final Logger log = LoggerFactory.getLogger(PrimesGeneratorHarness.class);

    BlockingQueue<PrimesResult> sharedQueue = new LinkedBlockingQueue<>();

    public void queueForProcessing(PrimesResult primesResult) {
        try {
            this.sharedQueue.put(primesResult);
            log.info("Queued {}", primesResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
            primesResult.setErrorMessage(e.getMessage());
        }
    }

    @PostConstruct
    public void initIt() {
        new Thread(new Consumer(this.sharedQueue))
                .start();
        log.info("Initialized PrimesGeneratorHarness");
    }

    protected List<Long> generatePrimes(Long upperLimit, PrimesGenerator.PrimesAlgorithm algorithm) {
        PrimesGenerator primesGenerator = null;
        switch (algorithm) {
            case FORK_JOIN:
                primesGenerator = new ForkJoinPrimesGenerator(upperLimit);
                break;
            case ITERATIVE:
                primesGenerator = new IterativePrimesGenerator(upperLimit);
                break;
            case STREAM:
                primesGenerator = new StreamPrimesGenerator(upperLimit);
                break;
            case PARALLEL_STREAM:
                primesGenerator = new ParallelStreamPrimesGenerator(upperLimit);
                break;
        }
        return primesGenerator.generatePrimes();
    }

    private class Consumer implements Runnable {

        private final BlockingQueue<PrimesResult> sharedQueue;

        public Consumer(BlockingQueue<PrimesResult> sharedQueue) {
            this.sharedQueue = sharedQueue;
        }

        @Override
        public void run() {
            while (true) {
                PrimesResult primesResult = null;
                try {
                    primesResult = sharedQueue.take();
                    log.info("Processing {}", primesResult);
                    Long startTime = System.nanoTime();
                    List<Long> primes = generatePrimes(primesResult.getUpperLimit(), primesResult.getAlgorithm());
                    Long endTime = System.nanoTime();
                    primesResult.addAll(primes);
                    primesResult.setProcessingTimeInNanoSeconds(endTime - startTime);
                    log.info("Processed {}", primesResult);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    primesResult.setErrorMessage(e.getMessage());
                }
            }
        }
    }
}
