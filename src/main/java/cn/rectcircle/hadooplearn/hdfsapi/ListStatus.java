package cn.rectcircle.hadooplearn.hdfsapi;

// cc ListStatus Shows the file statuses for a collection of paths in a Hadoop filesystem 
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

// vv ListStatus
/*
  （5） 通过FileSystem写文件或进度回调函数
  运行方式
  mvn package
  export HADOOP_CLASSPATH=target/experiment.jar
  hadoop cn.rectcircle.hadooplearn.hdfsapi.ListStatus hdfs://localhost/user/sunben/
  hadoop cn.rectcircle.hadooplearn.hdfsapi.ListStatus hdfs://localhost/user/sunben/test-copy.txt
*/
public class ListStatus {

  public static void main(String[] args) throws Exception {
    String uri = args[0];
    Configuration conf = new Configuration();
    FileSystem fs = FileSystem.get(URI.create(uri), conf);
    
    Path[] paths = new Path[args.length];
    for (int i = 0; i < paths.length; i++) {
      paths[i] = new Path(args[i]);
    }
    
    FileStatus[] status = fs.listStatus(paths);
    for (FileStatus s : status) {
      System.out.println(s);
    }
    System.out.println("======");

    Path[] listedPaths = FileUtil.stat2Paths(status);
    for (Path p : listedPaths) {
      System.out.println(p);
    }
  }
}
// ^^ ListStatus
