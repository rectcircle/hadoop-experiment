package cn.rectcircle.hadooplearn.hdfsapi;

// cc FileCopyWithProgress Copies a local file to a Hadoop filesystem, and shows progress
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

// vv FileCopyWithProgress
/*
  （4） 通过FileSystem写文件或进度回调函数
  运行方式
  mvn package
  export HADOOP_CLASSPATH=target/experiment.jar
  hadoop cn.rectcircle.hadooplearn.hdfsapi.FileCopyWithProgress input/docs/1400-8.txt hdfs://localhost/user/sunben/test-copy.txt
*/
public class FileCopyWithProgress {
  public static void main(String[] args) throws Exception {
    String localSrc = args[0];
    String dst = args[1];
    
    InputStream in = new BufferedInputStream(new FileInputStream(localSrc));
    
    Configuration conf = new Configuration();
    FileSystem fs = FileSystem.get(URI.create(dst), conf);
    // 创建或打开一个文件：将返回一个OutputStream，不允许seek
    OutputStream out = fs.create(new Path(dst), new Progressable() {
      public void progress() {
        System.out.print(".");
      }
    });
    IOUtils.copyBytes(in, out, 4096, true);
  }
}
// ^^ FileCopyWithProgress
