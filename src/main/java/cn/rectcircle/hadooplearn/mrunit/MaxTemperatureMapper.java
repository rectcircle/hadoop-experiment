package cn.rectcircle.hadooplearn.mrunit;

// cc MaxTemperatureMapperV4 Mapper for maximum temperature example
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// vv MaxTemperatureMapperV4
public class MaxTemperatureMapper
  extends Mapper<LongWritable, Text, Text, IntWritable> {
  
  enum Temperature {
    MALFORMED,
    TESTCOUNT
  }

  private NcdcRecordParser parser = new NcdcRecordParser();

  private boolean outed = false;

  private static final Log log = LogFactory.getLog(MaxTemperatureMapper.class);
  
  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
    
    parser.parse(value);
    if (parser.isValidTemperature()) {
      int airTemperature = parser.getAirTemperature();
      context.write(new Text(parser.getYear()), new IntWritable(airTemperature));

      if (!outed) {
        // 以下为日志输出测试
        context.getCounter(Temperature.TESTCOUNT).increment(1);
        System.out.println("这是标准输出"); // 输出到 userlogs 的 stdout 文件
        System.err.println("这是标准出错"); // 输出到 userlogs 的 stderr 文件
        context.setStatus("这是context.setStatus"); // 展示在web页面
        log.info("这个是Apache Log输出"); // 输出到 userlogs 的 syslog 文件
        // context.getConfiguration()
        outed = true;
      }
    } else if (parser.isMalformedTemperature()) {
      System.err.println("Ignoring possibly corrupt input: " + value);
      // 处理不合理的数据
      context.getCounter(Temperature.MALFORMED).increment(1);
    }
  }
}
// ^^ MaxTemperatureMapperV4
