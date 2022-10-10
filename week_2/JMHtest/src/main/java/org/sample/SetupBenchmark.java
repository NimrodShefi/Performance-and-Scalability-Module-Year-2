package org.sample;
import org.openjdk.jmh.annotations.Benchmark;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

import org.openjdk.jmh.annotations.OutputTimeUnit;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.ThreadLocalRandom;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Setup;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
//@Threads(2)

@State(Scope.Benchmark)
  public class SetupBenchmark{

  @Param({"10", "20", "30"})
  public int size;
  int[] array;

  @Setup
  public void setup() {
    array = new int[size];
    for (int c = 0; c < size; c++) {
        array[c] = c;
    }
  }

  @Benchmark
  public int add() {
      int acc = 0;
      for (int x : array) {
          acc += x;
      }
      return acc;
  }


}
