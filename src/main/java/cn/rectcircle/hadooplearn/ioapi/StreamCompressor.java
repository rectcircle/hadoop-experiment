package cn.rectcircle.hadooplearn.ioapi;

// cc StreamCompressor A program to compress data read from standard input and write it to standard output
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

// vv StreamCompressor
/*
（1）压缩
  运行方式
  mvn package
  unset HADOOP_CONF_DIR
  export HADOOP_CLASSPATH=target/experiment.jar
  echo "test" | hadoop cn.rectcircle.hadooplearn.ioapi.StreamCompressor org.apache.hadoop.io.compress.GzipCodec | gunzip
 */
public class StreamCompressor {

  public static void main(String[] args) throws Exception {
    
    String codecClassname = args[0];
    Class<?> codecClass = Class.forName(codecClassname);
    Configuration conf = new Configuration();
    // 通过反射加载args[0]的压缩器
    CompressionCodec codec = (CompressionCodec)
      ReflectionUtils.newInstance(codecClass, conf);
    // 装饰一个流
    CompressionOutputStream out = codec.createOutputStream(System.out);
    IOUtils.copyBytes(System.in, out, 4096, false);
    out.finish();
  }
}
// ^^ StreamCompressor
