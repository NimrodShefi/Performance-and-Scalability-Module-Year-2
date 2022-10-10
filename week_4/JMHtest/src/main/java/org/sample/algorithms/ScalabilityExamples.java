package org.sample;

import org.openjdk.jmh.annotations.Benchmark;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

import org.openjdk.jmh.annotations.OutputTimeUnit;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.annotations.Fork;

import java.util.concurrent.ThreadLocalRandom;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Setup;

import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)

@State(Scope.Benchmark)
public class ScalabilityExamples {

    @Param({"10", "20", "30", "40", "50", "60", "70", "80", "90", "100"})
    // @Param({"100", "200", "300", "400", "500", "600", "700", "800", "900", "1000"})
    public int arg;
    int[] array;
    double log;

    @Setup
    public void setup() {
      array = new int[arg];
      for (int i = 0; i < arg; i++) {
          array[i] = ThreadLocalRandom.current().nextInt();
      }
      log = Math.log(arg)/Math.log(2);
      System.out.println(log);
    }

    @Benchmark
    public void linearScaling(Blackhole bh) {
        for (int i = 0; i < arg; i++) {
          bh.consume(array[i]);
        }
    }

    @Benchmark
    public void quadraticScaling(Blackhole bh) {
      for (int i = 0; i < arg; i++) {
        for (int j = 0; j < arg; j++) {
          bh.consume(array[i]);
        }
      }
    }

    @Benchmark
    public void cubicScaling(Blackhole bh) {
      for (int i = 0; i < arg; i++) {
        for (int j = 0; j < arg; j++) {
          for (int k = 0; k < arg; k++) {
            bh.consume(array[i]);
          }
        }
      }
    }

    // The timings for the below will be very small; will need to do experiments
    // With different (larger) values of N
    @Benchmark
    public void logarithmicScaling(Blackhole bh) {
      for (double i = 0; i < log; i++) {
        bh.consume(i);
      }
    }

}
