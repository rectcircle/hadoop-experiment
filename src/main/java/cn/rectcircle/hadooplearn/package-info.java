/**
 * 实验环境准备
 * * 安装JDK
 * * 安装Hadoop
 * 
```
cd ~
mkdir hadoop
cd hadoop
wget http://mirror.bit.edu.cn/apache/hadoop/common/hadoop-2.6.5/hadoop-2.6.5.tar.gz
tar -xzvf hadoop-2.6.5.tar.gz
ln -s hadoop-2.6.5 default
vim ~/.bashrc
# 添加如下环境变量
## Hadoop
#export HADOOP_HOME=/home/bigdata/hadoop/default
#export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
#export PATH="$HADOOP_HOME/bin:$PATH"
#export HADOOP_OPTS="-Djava.library.path=$HADOOP_HOME/lib:$HADOOP_COMMON_LIB_NATIVE_DIR"
source ~/.bashrc
hadoop
```
 */
package cn.rectcircle.hadooplearn;
