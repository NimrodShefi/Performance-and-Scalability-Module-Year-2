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


// @BenchmarkMode(Mode.SampleTime)
// @BenchmarkMode({Mode.SingleShotTime, Mode.AverageTime, Mode.SampleTime})
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)

@State(Scope.Benchmark)
public class ExerciseBenchmark{

  @Param({"10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "200", "400", "600"  })
  public int arg;
  // start of week 3 exercise:
  // make it so that when Benchmarking the method matMult doesn't inlcude the createArray method in the timing
  public double matMultFirstArray[][];
  public double matMultSecondArray[][];
  public double result[][];

  @Setup
  public void setup(){
    matMultFirstArray = new double[arg][arg];
    matMultSecondArray = new double[arg][arg];
    result = new double[arg][arg];
    for (int j = 0; j < arg; j++) {
        for (int k = 0; k < arg; k++) {
          matMultFirstArray[j][k] = ThreadLocalRandom.current().nextDouble();
          matMultSecondArray[j][k] = ThreadLocalRandom.current().nextDouble();
        }
    }
  }

 @Benchmark
 public double createArray() {
   double[] array = new double[arg];
   for (int k = 0; k < arg; k++) {
      array[k] = ThreadLocalRandom.current().nextDouble();
   }
   return array[arg-1];
 }

 @Benchmark
 public double createMat(){
   double [][] result = createArray(arg,arg);
   return result[arg-1][arg-1];
 }

 @Benchmark
 public double matMult(){
   // the code that was here beofre, where we set up the matrix and result was moved to the setup to allow to complete the exercise explained above
   /*
   double [][] result = new double[arg][arg];
   double firstarray[][] = createArray(arg,arg);
   double secondarray[][] = createArray(arg,arg);
   */
   for (int i = 0; i < matMultFirstArray.length; i++) {
       for (int j = 0; j < matMultSecondArray[0].length; j++) {
           for (int k = 0; k < matMultFirstArray[0].length; k++) {
               result[i][j] += matMultFirstArray[i][k] * matMultSecondArray[k][j];
           }
       }
   }
   return result[arg-1][arg-1];
 }



 public static double[][] createArray(int x, int y){
   double [][] result = new double[x][y];
   for (int j = 0; j < x; j++) {
       for (int k = 0; k < y; k++) {
           result[j][k] = ThreadLocalRandom.current().nextDouble();
       }
   }
   return result;
 }

}
