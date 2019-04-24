package cn.rectcircle.hadooplearn.hdfsapi;

// cc FileSystemCat Displays files from a Hadoop filesystem on standard output by using the FileSystem directly
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

// vv FileSystemCat
// （7） 通过Glob匹配文件可以配合PathFilter使用
// 运行方式
// mvn package
// export HADOOP_CLASSPATH=target/experiment.jar
// hadoop cn.rectcircle.hadooplearn.hdfsapi.FileSystemGlobAndPathFilter hdfs://localhost/user/sunben/ '*'
// hadoop cn.rectcircle.hadooplearn.hdfsapi.FileSystemGlobAndPathFilter hdfs://localhost/user/sunben/ '*/*/*' '.*tuples.*'
public class FileSystemGlobAndPathFilter {
  public static void main(String[] args) throws Exception {
    String uri = args[0];
    Configuration conf = new Configuration();
    FileSystem fs = FileSystem.get(URI.create(uri), conf);
    FileStatus[] status = null;

    // Arrays.stream(args).forEach(System.out::println);

    if (args.length == 2) {
      status = fs.globStatus(new Path(args[1]));
    } else if(args.length == 3) {
      status = fs.globStatus(new Path(args[1]), new RegexPathFilter(args[2]));
    }
    if (status == null) {
      System.out.println("No Path Match");
      return;
    }
    Path[] listedPaths = FileUtil.stat2Paths(status);
    for (Path p : listedPaths) {
      System.out.println(p);
    }
  }
}
// ^^ FileSystemCat
