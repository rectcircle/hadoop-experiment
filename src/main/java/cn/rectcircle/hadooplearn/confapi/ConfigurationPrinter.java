package cn.rectcircle.hadooplearn.confapi;

// cc ConfigurationPrinter An example Tool implementation for printing the properties in a Configuration
import java.util.Map.Entry;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.util.*;

// vv ConfigurationPrinter
/*
（2）ToolRunner，将会将使用一个命令行参数解析器（GenericOptionsParser），
将Hadoop的相关配置配置到Tool的conf中（Tool接口继承了Configurable接口）
支持如下Hadoop参数赋值方式：

-D p=v 配置单个属性
-conf filename ... XML配置文件
-fs uri  等价于 -D fs.default.FS=uri

其他参见 Hadoop 权威指南 152 页


运行方式：
mvn package
export HADOOP_CLASSPATH=target/experiment.jar
hadoop cn.rectcircle.hadooplearn.confapi.ConfigurationPrinter | grep 'fs.defaultFS'
export HADOOP_CONF_DIR=$(pwd)/src/main/resources/pseudo-distribute-conf
hadoop cn.rectcircle.hadooplearn.confapi.ConfigurationPrinter | grep 'fs.defaultFS'
unset HADOOP_CONF_DIR
# 使用conf
hadoop cn.rectcircle.hadooplearn.confapi.ConfigurationPrinter -conf src/main/resources/conf/hadoop-local.xml | grep 'fs.defaultFS'
hadoop cn.rectcircle.hadooplearn.confapi.ConfigurationPrinter -conf src/main/resources/conf/hadoop-localhost.xml | grep 'fs.defaultFS'
*/
public class ConfigurationPrinter extends Configured implements Tool {
  
  static {
    Configuration.addDefaultResource("hdfs-default.xml");
    Configuration.addDefaultResource("hdfs-site.xml");
    Configuration.addDefaultResource("yarn-default.xml");
    Configuration.addDefaultResource("yarn-site.xml");
    Configuration.addDefaultResource("mapred-default.xml");
    Configuration.addDefaultResource("mapred-site.xml");
  }

  @Override
  public int run(String[] args) throws Exception {
    Configuration conf = getConf();
    for (Entry<String, String> entry: conf) {
      System.out.printf("%s=%s\n", entry.getKey(), entry.getValue());
    }
    return 0;
  }
  
  /*
  该程序用于打印hadoop的配置信息
  使用Hadoop命令运行
  ```
  mvn compile
  export HADOOP_CLASSPATH=target/classes
  hadoop cn.rectcircle.hadooplearn.apilearn.ConfigurationPrinter \
    -conf conf/hadoop-localhost.xml | grep yarn.resourcemanager.address
  hadoop cn.rectcircle.hadooplearn.apilearn.ConfigurationPrinter \
    -D color=yellow | grep yellow
  # 注意这个 -D 可以有空格
  # 通过JVM-D配置是无效的
  ```
  */
  public static void main(String[] args) throws Exception {
    // 添加通用解析，比如-conf。
    int exitCode = ToolRunner.run(new ConfigurationPrinter(), args);
    System.exit(exitCode);
  }
}
// ^^ ConfigurationPrinter