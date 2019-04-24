# Hadoop 实验代码

> 《Hadoop权威指南》
> [hadoop-book](https://github.com/tomwhite/hadoop-book.git)

## 说明

* 默认使用Hadoop本地模式运行测试代码

## MAC OSX 问题和BUG

* 本地库无法使用，如果需要使用需要重新编译 [doc](http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/NativeLibraries.html)
* 使用Java实现的IO库的情况下，无法使用Gzip压缩Map输出 [bug](https://jira.apache.org/jira/browse/HADOOP-11334)
* `$HADOOP_HOME/libexec/hadoop-config.sh:157` Yarn Java环境变量问题 [bug](https://stackoverflow.com/questions/33968422/bin-bash-bin-java-no-such-file-or-directory)
  * 修改以上文件（似乎还是不行）
  * `sudo ln -s /usr/bin/java /bin/java`
* 伪分布式，MR如果卡在 INFO mapreduce.Job: Running job
  * `/etc/hosts` 将127.0.0.1 后面添加 `$(hostname)`

## 阅读指南

* Hadoop安装指南：src/main/java/cn/rectcircle/hadooplearn/package-info.java
* 伪分布式配置： src/main/resources/pseudo-distribute-conf/README
* 用户连接指南： src/main/resources/conf/READEME
* MR介绍： src/main/java/cn/rectcircle/hadooplearn/mrintro/package-info.java
* hdfs API介绍： /Users/sunben/Workspace/learn/hadoop/experiment/src/main/java/cn/rectcircle/hadooplearn/hdfsapi/package-info.java
* HadoopIO API介绍： src/main/java/cn/rectcircle/hadooplearn/ioapi/package-info.java
* Configuration API介绍： src/main/java/cn/rectcircle/hadooplearn/confapi/package-info.java
* 单元测试 src/main/java/cn/rectcircle/hadooplearn/mrunit/package-info.java
