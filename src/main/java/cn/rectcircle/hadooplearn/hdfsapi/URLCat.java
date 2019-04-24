package cn.rectcircle.hadooplearn.hdfsapi;

// cc URLCat Displays files from a Hadoop filesystem on standard output using a URLStreamHandler
import java.io.InputStream;
import java.net.URL;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;

// vv URLCat
/*
  （1） 通过URI获取一个IO对象
  运行方式
  mvn package
  export HADOOP_CLASSPATH=target/experiment.jar
  hadoop cn.rectcircle.hadooplearn.hdfsapi.URLCat file:///etc/hosts
  hadoop cn.rectcircle.hadooplearn.hdfsapi.URLCat hdfs://localhost/user/sunben/input/ncdc/sample.txt
*/
public class URLCat {

  static {
    URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
  }
  
  public static void main(String[] args) throws Exception {
    InputStream in = null;
    try {
      in = new URL(args[0]).openStream();
      IOUtils.copyBytes(in, System.out, 4096, false);
    } finally {
      IOUtils.closeStream(in);
    }
  }
}
// ^^ URLCat
