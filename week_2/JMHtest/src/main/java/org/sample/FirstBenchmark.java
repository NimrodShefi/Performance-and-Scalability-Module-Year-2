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

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)

public class FirstBenchmark {

  // @Benchmark
  public void blank() {
    // this is a blank test
  }

  // @Benchmark
  public void a_log(){
    double x = Math.log(42);
  }

  // @Benchmark
  public double b_returnLog(){
    return Math.log(42);
  }

  // @Benchmark
  public void consumeLog(Blackhole bh){
    bh.consume(Math.log(42));
  }

  private static volatile int testNumVol = 42;
  // @Benchmark
  public double retVolVarLog() {
     return Math.log(testNumVol);
  }

}
