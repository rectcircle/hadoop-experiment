package cn.rectcircle.hadooplearn.confapi;

import org.apache.hadoop.conf.Configuration;

/*
（1）Configuration
 */
public class ConfigurationAPI {
	public static void main(String[] args) {
		// 配置API：主要用来读取XML格式的配置
		Configuration conf = new Configuration();
		conf.addResource("configuration-1.xml");
		System.out.println(conf.get("color"));
		// 后面加入的属性会覆盖前面的属性，final为true的除外
		conf.addResource("configuration-2.xml");
		// XML支持使用${属性名}插值
		System.out.println(conf.get("size-weight"));
		// XML使用${属性名}插值，同样支持系统变量和JVM参数：-D参数名=value
		// conf无法拿到系统变量
		System.setProperty("size", "14");
		System.out.println(conf.get("size-weight"));

		// Hadoop 启动过程中，将使用addResource将conf目录下的所有文件读取进入conf中
		// 配置文件的位置可以通过 HADOOP_CONF_DIR 环境变量配置
		// 配置属性一般分为两类：
		//   集群属性，只能通过配置文件改变
		//   任务属性，可以通过提交的程序修改
	}
}