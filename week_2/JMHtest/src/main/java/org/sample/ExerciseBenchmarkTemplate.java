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



// @BenchmarkMode(Mode.SampleTime)
// @BenchmarkMode({Mode.SingleShotTime, Mode.AverageTime, Mode.SampleTime})
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)

@State(Scope.Benchmark)
public class ExerciseBenchmarkTemplate {

 @Param({"10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "200", "400", "600"})
 public int arg;

 // @Benchmark
 public double createArray() {
   double[] array = new double[arg];
   for (int k = 0; k < arg; k++) {
      array[k] = ThreadLocalRandom.current().nextDouble();
   }
   return array[arg-1];
 }

 // @Benchmark
 public double createMat(){
   double[][] result = createArray(arg, arg);
   return result[arg-1][arg-1];
 }

 //Benchmark
 public double matMult(){
   return 0.0;
 }

 public double[][] createArray(int x, int y) {
   double[][] result = new double[x][y];
   for (int i = 0; i < x; i++) {
     for (int j = 0; j < y; j++) {
       result[i][j] = ThreadLocalRandom.current().nextDouble();
     }
   }
   return result;
 }


}
