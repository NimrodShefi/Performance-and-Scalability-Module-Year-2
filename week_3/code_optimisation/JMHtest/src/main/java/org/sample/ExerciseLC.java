package org.sample;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Scope;

import org.openjdk.jmh.infra.Blackhole;



@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)

@State(Scope.Benchmark)
public class ExerciseLC{

  @State(Scope.Thread)
  public static class ThreadState{
    volatile int k = 199;
  }

  @Benchmark
  public int bad(ThreadState state, Blackhole bh){
    return matBad(state.k);
  }


  public Integer matBad(int size){
    Integer[][] mat = new Integer[size][size];
    // populate mat
    for (int j = 0; j < mat[0].length ; j++){
      for (int i = 0; i < mat.length ; i++){
        mat[i][j]= i*j/4 ;
      }
    }
    // scale and sum the matrix
    matScale(mat, size);
    return matSum(mat);
  }


  public void matScale(Integer[][] mat, Integer scale) {
    for (int j = 0; j < mat[0].length ; j++){
      for (int i = 0; i <  mat.length ; i++){
        mat[i][j]=mat[i][j] * scale;
      }
    }
  }


  public int matSum(Integer[][] mat) {
    int k =0;
    for (int j = 0; j < mat[0].length ; j++){
      for (int i = 0; i <  mat.length ; i++){
        k += mat[i][j];
      }
    }
    return k;
  }

  // Louise's Version:
  @Benchmark
  public int better(ThreadState state, Blackhole bh){
    return matBetter(state.k);
  }


  public Integer matBetter(int size){
    Integer[][] mat = new Integer[size][size];
    // populate mat
    for (int i = 0; i < mat.length ; i++){
      for (int j = 0; j < mat[0].length ; j++){
        mat[i][j]= i*j/4 ;
      }
    }
    // scale and sum the matrix
    matScaleBetter(mat, size);
    return matSumBetter(mat);
  }


  public void matScaleBetter(Integer[][] mat, Integer scale) {
    for (int i = 0; i < mat.length ; i++){
      for (int j = 0; j < mat[0].length ; j++){
        mat[i][j]=mat[i][j] * scale;
      }
    }
  }


  public int matSumBetter(Integer[][] mat) {
    int k =0;
    for (int i = 0; i < mat.length ; i++){
      for (int j = 0; j < mat[0].length ; j++){
        k += mat[i][j];
      }
    }
    return k;
  }

}
