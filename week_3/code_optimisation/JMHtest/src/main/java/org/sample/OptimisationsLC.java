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
import org.openjdk.jmh.annotations.Setup;

import org.openjdk.jmh.infra.Blackhole;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)

@State(Scope.Benchmark)
public class OptimisationsLC{

  @State(Scope.Thread)
  public static class ThreadState{
    volatile int int_k = 199;
    volatile int[] ints = new int[1000];
    volatile int[][] mat = new int[1000][1000];
    volatile int[][] result = new int[1000][1000];
    Integer integer;
  }

  // @Benchmark
  public void blank(){
    // this is a blank test
  }


  // ------------------------------------------------------------------------------------
  // Loop tests

  // @Setup
  public void setup(ThreadState state){
    for (int i = 0;i < state.ints.length;i++){
      state.ints[i]=i;
    }
  }

  // @Benchmark
  public int a_testLoop(ThreadState state) {
    int k = state.int_k;
    for (int i=0; i < state.ints.length; i++) {
      k+=state.ints[i];
    }
    return k;
  }

  // @Benchmark
  public int b_testLoopVar(ThreadState state) {
    int len = state.ints.length;
    int k = state.int_k;
    for (int i=0; i < len; i++) {
      k+=state.ints[i];
    }
    return k;
  }

  // @Benchmark
  public int testLoopEach(ThreadState state) {
    int k = state.int_k;
    for (int i : state.ints) {
      k+=i;
    }
    return k;
  }
  // ------------------------------------------------------------------------------------
  // ------------------------------------------------------------------------------------
  // Loop Ordering tests.

  // @Setup
  public void setupMat(ThreadState state){
    for (int i = 0;i < state.mat.length;i++){
      for (int j = 0;j < state.mat[0].length;j++){
        state.mat[i][j]=i+j;
      }
    }
  }


/*
example of a 2D array (matrix):
array x: x1 x2 x3 x4
array z: z1 z2 z3 z4
array y: y1 y2 y3 y4
array j: j1 j2 j3 j4

java expects to go along an array before going down to the next array
*/

  // @Benchmark
  public int[][] matAdd1(ThreadState state) { // this goes up and down the matrix rather than along
    int k = state.int_k;
    int n = state.mat.length;
    int m = state.mat[0].length;
    for (int j = 0; j < m ;j++){
      for (int i = 0; i < n ;i++){
        state.result[i][j]=state.mat[i][j]+state.mat[i][j];
      }
    }
    return state.result;
  }

  // @Benchmark
  public int[][] matAdd2(ThreadState state) { // this goes along the matrix like expected in java from a 2D array
    int k = state.int_k;
    int n = state.mat.length;
    int m = state.mat[0].length;
    for (int i = 0; i < n; i++){
      for (int j = 0; j < m; j++){
        state.result[i][j]=state.mat[i][j]+state.mat[i][j];
      }
    }
    return state.result;
  }



  // ------------------------------------------------------------------------------------
  // ------------------------------------------------------------------------------------
    // operators tests

    @Benchmark
    public void a_BlankConsume(ThreadState state, Blackhole bh) {
      bh.consume( state.int_k );
    }

    @Benchmark
    public void b_x2(ThreadState state, Blackhole bh) {
      bh.consume( state.int_k*2 );
    }

   @Benchmark
    public void c_shift1(ThreadState state, Blackhole bh) {
        bh.consume(state.int_k << 1); // binary shif which equals to multiply by 2
    }

   @Benchmark
    public void d_x32(ThreadState state, Blackhole bh) {
      bh.consume(state.int_k*32);
    }

    @Benchmark
    public void e_shift5(ThreadState state, Blackhole bh) {
      bh.consume(state.int_k << 5); // binary shif which equals to multiply by 32
    }

    @Benchmark
    public void f_div2(ThreadState state, Blackhole bh) {
        bh.consume(state.int_k/2);
    }

    @Benchmark
    public void g_shift_1(ThreadState state, Blackhole bh) {
      bh.consume(state.int_k >> 1); // binary shif which equals to divide by 2
    }

    @Benchmark
    public void h_div4(ThreadState state, Blackhole bh) {
        bh.consume(state.int_k/4);
    }

    @Benchmark
    public void i_shift_2(ThreadState state, Blackhole bh) {
      bh.consume(state.int_k >> 2); // binary shif which equals to divide by 4
    }


// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------
// Autoboxing tests

    @Setup
    public void setupAutoBox(ThreadState state){
      state.integer = new Integer(state.int_k);
    }

    @Benchmark
    public int a_intReturn(ThreadState state) {
      return state.int_k;
    }

    @Benchmark
    public Integer b_ObjReturn(ThreadState state) {
      return state.integer;
    }

    @Benchmark
    public Integer c_autoBox(ThreadState state) { // this takes longer than the 2 methods above as the compiler adds code that convert int to Integer
      return state.int_k;
    }

//---------------- Do above first ------ then these two. -----
//---------------- Autoboxing in context ---------------------
    // @Benchmark
    public void intArrayGeneration(ThreadState state, Blackhole bh) {
      int n = state.int_k;
      int[] array = new int[n];
      for (int i = 0; i < n; i++){
        array[i] = i;
      }
      bh.consume(array);
    }

    // @Benchmark
    public void IntegerArrayGeneration(ThreadState state, Blackhole bh) {
      int n = state.int_k;
      Integer[] array = new Integer[n];
      for (int i = 0; i < n; i++){
        array[i] = i;
      }
      bh.consume(array);
    }



}
