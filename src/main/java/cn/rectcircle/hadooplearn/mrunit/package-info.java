/**
 * 6、Hadoop 单元测试 参见
 * @see src/test/java/cn/rectcircle/hadooplearn/mrunit/MaxTemperatureMapperTest.java
 * 
 * 6、工作流
 * ChainMapper和 ChainReducer 组合多个M和多个R
 * 
 * 对于简单线性依赖关系的一组MR任务，可以考虑使用JobControl及相关API使用代码构造依赖关系
 * 
 * 还有专门的工作流引擎来实现如：Oozie 和 Azkaban
 */
package cn.rectcircle.hadooplearn.mrunit;
