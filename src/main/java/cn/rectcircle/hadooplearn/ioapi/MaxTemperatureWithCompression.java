package cn.rectcircle.hadooplearn.ioapi;

import org.apache.hadoop.conf.Configuration;
// cc MaxTemperatureWithCompression Application to run the maximum temperature job producing compressed output
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.DefaultCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import cn.rectcircle.hadooplearn.mrintro.MaxTemperature;
import cn.rectcircle.hadooplearn.mrintro.MaxTemperatureMapper;
import cn.rectcircle.hadooplearn.mrintro.MaxTemperatureReducer;

//vv MaxTemperatureWithCompression
/*
（2） MR使用压缩（map输出、结果输出）
  mvn package
  export HADOOP_CLASSPATH=target/experiment.jar
  hadoop cn.rectcircle.hadooplearn.ioapi.MaxTemperatureWithCompression input/ncdc/sample.txt.gz output
  gunzip -c output/part-r-00000.gz
  rm -rf output
*/
public class MaxTemperatureWithCompression {

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: MaxTemperatureWithCompression <input path> " + "<output path>");
      System.exit(-1);
    }
    // 启用Map输出压缩
    Configuration conf = new Configuration();
    conf.setBoolean(Job.MAP_OUTPUT_COMPRESS, true);
    // 在此配置GZIP将报错
    // https://jira.apache.org/jira/browse/HADOOP-11334
    conf.setClass(Job.MAP_OUTPUT_COMPRESS_CODEC, DefaultCodec.class, CompressionCodec.class);

    Job job = Job.getInstance(conf);
    job.setJarByClass(MaxTemperature.class);

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    
    // 设置压缩
    /*[*/FileOutputFormat.setCompressOutput(job, true);
    FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);/*]*/
    
    job.setMapperClass(MaxTemperatureMapper.class);
    job.setCombinerClass(MaxTemperatureReducer.class);
    job.setReducerClass(MaxTemperatureReducer.class);
    
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
//^^ MaxTemperatureWithCompression
