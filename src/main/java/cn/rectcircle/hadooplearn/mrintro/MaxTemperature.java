package cn.rectcircle.hadooplearn.mrintro;

// cc MaxTemperature Application to find the maximum temperature in the weather dataset
// vv MaxTemperature
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MaxTemperature {

  /*
  运行方式
  mvn package
  export HADOOP_CLASSPATH=target/experiment.jar
  hadoop cn.rectcircle.hadooplearn.mrintro.MaxTemperature input/ncdc/sample.txt output
  rm -rf output
  */
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: MaxTemperature <input path> <output path>");
      System.exit(-1);
    }

    // 创建一个Job
    Job job = Job.getInstance();
    // 设置要分发的Jar
    job.setJarByClass(MaxTemperature.class);
    // 设置Job名
    job.setJobName("Max temperature");

    // 设置输入输出目录
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    
    // 配置MR
    job.setMapperClass(MaxTemperatureMapper.class);
    job.setReducerClass(MaxTemperatureReducer.class);

    // 设置输出的KV类型
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    
    // 启动Job并等待Job完成
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
// ^^ MaxTemperature
