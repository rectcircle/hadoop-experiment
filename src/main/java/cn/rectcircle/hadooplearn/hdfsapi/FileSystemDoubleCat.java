package cn.rectcircle.hadooplearn.hdfsapi;

// cc FileSystemDoubleCat Displays files from a Hadoop filesystem on standard output twice, by using seek
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

// vv FileSystemDoubleCat
/*
  （3） 通过seek方法
  运行方式
  mvn package
  export HADOOP_CLASSPATH=target/experiment.jar
  hadoop cn.rectcircle.hadooplearn.hdfsapi.FileSystemDoubleCat file:///etc/hosts
  hadoop cn.rectcircle.hadooplearn.hdfsapi.FileSystemDoubleCat hdfs://localhost/user/sunben/input/ncdc/sample.txt
*/
public class FileSystemDoubleCat {

  public static void main(String[] args) throws Exception {
    String uri = args[0];
    Configuration conf = new Configuration();
    FileSystem fs = FileSystem.get(URI.create(uri), conf);
    FSDataInputStream in = null;
    try {
      in = fs.open(new Path(uri));
      IOUtils.copyBytes(in, System.out, 4096, false);
      in.seek(0); // go back to the start of the file
      IOUtils.copyBytes(in, System.out, 4096, false);
    } finally {
      IOUtils.closeStream(in);
    }
  }
}
// ^^ FileSystemDoubleCat
