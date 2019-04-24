/**
 * 02.hdfs API 学习
 * 前置条件：使用伪分布式运行HDFS
 * 
 * （8）一致性
 * Path p =  new Path("p");
 * FSDataOutputStream out = fs.create(p);
 * out.write("123");
 * out.hflush(); # 保证写入对所有Reader可见（仅保证写到内存）
 * out.hsync(); # 保证写入磁盘
 * ```
 */
package cn.rectcircle.hadooplearn.hdfsapi;
