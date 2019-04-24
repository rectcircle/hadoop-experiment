package cn.rectcircle.hadooplearn.iotypes;

// == NonSplittableTextInputFormat
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

/*
  （3）不进行分片的TextInputFormat
*/
public class NonSplittableTextInputFormat extends TextInputFormat {
  @Override
  protected boolean isSplitable(JobContext context, Path file) {
    return false;
  }
}
