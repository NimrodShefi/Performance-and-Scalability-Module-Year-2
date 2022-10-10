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


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)

@State(Scope.Benchmark)
public class ParametersBenchmark{

  @Param({"10", "20", "30"}) // this is used to see how the application scales for when args equals 10, 20 & 30
  public int arg;

  // @Benchmark
  public double createArray() {
     double[] array = new double[arg];
     for (int k = 0; k < arg; k++) {
        array[k] = ThreadLocalRandom.current().nextDouble();
     }
     return array[arg-1];
  }
}
