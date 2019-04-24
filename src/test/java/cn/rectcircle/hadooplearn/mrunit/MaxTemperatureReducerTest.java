package cn.rectcircle.hadooplearn.mrunit;
// == MaxTemperatureReducerTestV1
import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.*;

/*
Reducer的单元测试
 */
public class MaxTemperatureReducerTest {
  
  //vv MaxTemperatureReducerTestV1
  @Test
  public void returnsMaximumIntegerInValues() throws IOException,
      InterruptedException {
    new ReduceDriver<Text, IntWritable, Text, IntWritable>()
      .withReducer(new MaxTemperatureReducer())
      .withInput(new Text("1950"),
          Arrays.asList(new IntWritable(10), new IntWritable(5)))
      .withOutput(new Text("1950"), new IntWritable(10))
      .runTest();
  }
  //^^ MaxTemperatureReducerTestV1
}
