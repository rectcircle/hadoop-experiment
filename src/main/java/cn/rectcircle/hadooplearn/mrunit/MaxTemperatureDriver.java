package cn.rectcircle.hadooplearn.mrunit;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/*
mvn package
export HADOOP_CLASSPATH=target/experiment.jar
hadoop cn.rectcircle.hadooplearn.mrunit.MaxTemperatureDriver -conf src/main/resources/conf/hadoop-local.xml input/ncdc/micro output
rm -rf output
# 或者
hadoop cn.rectcircle.hadooplearn.mrunit.MaxTemperatureDriver -fs file:/// input/ncdc/micro output
rm -rf output

# 使用jar方式运行(集群运行采用此方式)
hadoop jar target/experiment.jar cn.rectcircle.hadooplearn.mrunit.MaxTemperatureDriver -conf src/main/resources/conf/hadoop-local.xml input/ncdc/micro output

# 使用集群方式运行
hadoop jar target/experiment.jar cn.rectcircle.hadooplearn.mrunit.MaxTemperatureDriver -conf src/main/resources/conf/hadoop-localhost.xml input/ncdc/all max-temp
hadoop fs -conf src/main/resources/conf/hadoop-localhost.xml -ls max-temp
hadoop fs -conf src/main/resources/conf/hadoop-localhost.xml  -rm -r max-temp
# 相关ID：

应用ID application_{时间戳}_{自增ID(1开始)}
作业ID job_{应用ID后缀}
任务ID task_{作业ID后缀}_{m|r}_{第几个map或者reduce, 0开始计数}
重试ID attempt_{任务ID后缀}_{第几次重试，0开始计数}


通过web页面查看状态
http://localhost:8088/

# 日志
聚合服务（将日志文件写入HDFS、默认关闭）：
yarn.log-aggregation-enable=true， 这样Logs链接将可以查看输出日志
默认情况下存放在 HADOOP_LOG_DIR （默认在$HADOOP_HOME/logs）
根目录下为 集群日志
userlogs为任务、应用日志

配置日志输出级别： hadoop -D mapreduce.map.log.level=DEBUG
配置日志输出到命令行：export HADOOP_ROOT_LOGGER=DEBUG,console

# 启用分析
hadoop jar target/experiment.jar cn.rectcircle.hadooplearn.mrunit.MaxTemperatureDriver -conf src/main/resources/conf/hadoop-localhost.xml -D mapreduce.task.profile=true input/ncdc/all max-temp
hadoop fs -conf src/main/resources/conf/hadoop-localhost.xml  -rm -r max-temp

输出文件和stdout在同一目录
*/
public class MaxTemperatureDriver extends Configured implements Tool {

  @Override
  public int run(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.printf("Usage: %s [generic options] <input> <output>\n", getClass().getSimpleName());
      ToolRunner.printGenericCommandUsage(System.err);
      return -1;
    }
    // 设置一个作业名
    Job job = Job.getInstance(getConf(), "Max temperature");
    job.setJarByClass(getClass());

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    job.setMapperClass(MaxTemperatureMapper.class);
    job.setCombinerClass(MaxTemperatureReducer.class);
    job.setReducerClass(MaxTemperatureReducer.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    return job.waitForCompletion(true) ? 0 : 1;
  }

  public static void main(String[] args) throws Exception {
    int exitCode = ToolRunner.run(new MaxTemperatureDriver(), args);
    System.exit(exitCode);
  }
}
