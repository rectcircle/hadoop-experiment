package cn.rectcircle.hadooplearn.hdfsapi;

// cc FileSystemCat Displays files from a Hadoop filesystem on standard output by using the FileSystem directly

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

// vv FileSystemCat
/*
  （6） 通过FileSystem删除文件
  运行方式
  mvn package
  export HADOOP_CLASSPATH=target/experiment.jar
  hadoop cn.rectcircle.hadooplearn.hdfsapi.FileSystemDelete hdfs://localhost/user/sunben/test-copy.txt
*/
public class FileSystemDelete {

  public static void main(String[] args) throws Exception {
    String uri = args[0];
    Configuration conf = new Configuration();
    FileSystem fs = FileSystem.get(conf);
    fs.delete(new Path(uri), true);
  }
}
// ^^ FileSystemCat
