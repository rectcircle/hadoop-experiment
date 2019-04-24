package cn.rectcircle.hadooplearn.mrfeatures;

// cc JoinStationMapper Mapper for tagging station records for a reduce-side join
import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import cn.rectcircle.hadooplearn.ioapi.TextPair;


// vv JoinStationMapper
public class JoinStationMapper
    extends Mapper<LongWritable, Text, TextPair, Text> {
  private NcdcStationMetadataParser parser = new NcdcStationMetadataParser();

  @Override
  protected void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
    if (parser.parse(value)) {
      // 将数据数据来源标记到key中
      context.write(new TextPair(parser.getStationId(), "0"),
          new Text(parser.getStationName()));
    }
  }
}
// ^^ JoinStationMapper