package cn.rectcircle.hadooplearn.iotypes;

// cc WholeFileInputFormat An InputFormat for reading a whole file as a record
import java.io.IOException;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.*;

//vv WholeFileInputFormat
/*
  （4）自定义InputFormat：将一个文件当做Map的一个输入
 */
public class WholeFileInputFormat
    extends FileInputFormat<NullWritable, BytesWritable> {
  
  // 不进行切分
  @Override
  protected boolean isSplitable(JobContext context, Path file) {
    return false;
  }

  // 返回一个自定义的记录迭代器
  @Override
  public RecordReader<NullWritable, BytesWritable> createRecordReader(
      InputSplit split, TaskAttemptContext context) throws IOException,
      InterruptedException {
    WholeFileRecordReader reader = new WholeFileRecordReader();
    reader.initialize(split, context);
    return reader;
  }
}
//^^ WholeFileInputFormat
