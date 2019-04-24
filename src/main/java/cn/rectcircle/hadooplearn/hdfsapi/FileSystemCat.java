package cn.rectcircle.hadooplearn.hdfsapi;

// cc FileSystemCat Displays files from a Hadoop filesystem on standard output by using the FileSystem directly
import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

// vv FileSystemCat
/*
  （2） 通过FileSystem读文件
  运行方式
  mvn package
  export HADOOP_CLASSPATH=target/experiment.jar
  hadoop cn.rectcircle.hadooplearn.hdfsapi.FileSystemCat file:///etc/hosts
  hadoop cn.rectcircle.hadooplearn.hdfsapi.FileSystemCat hdfs://localhost/user/sunben/input/ncdc/sample.txt
*/
public class FileSystemCat {

  public static void main(String[] args) throws Exception {
    String uri = args[0];
    Configuration conf = new Configuration();
    FileSystem fs = FileSystem.get(URI.create(uri), conf);
    InputStream in = null;
    try {
      in = fs.open(new Path(uri));
      IOUtils.copyBytes(in, System.out, 4096, false);
    } finally {
      IOUtils.closeStream(in);
    }
  }
}
// ^^ FileSystemCat
