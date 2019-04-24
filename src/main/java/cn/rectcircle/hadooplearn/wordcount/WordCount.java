package cn.rectcircle.hadooplearn.wordcount;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


public class WordCount {

	//Map过程继承Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
	//实现map方法
	public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		//需要覆写的函数，Mapper的主体
		//声明为void map(KEYIN key, VALUEIN value, Context context)
		//用户输入的数据源使用InputSplit进行分片，存储每个分片的长度和偏移量的数组
		//参数key、value由InputFormat针对每个分片产生，默认的InputFormat为TextInputFormat,行为为按照换行进行分割，结果为<分片偏移量, line>
		//结果写入context即可
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			//默认情况下value为一行数据
			// StringTokenizer创建一个类似迭代器的对象，按照字符串的制表回车换行的字符分割
			StringTokenizer itr = new StringTokenizer(value.toString());
			while (itr.hasMoreTokens()) {
				//设置Text，Text应该是一个可变对象
				word.set(itr.nextToken());
				//将数据写入上下文
				context.write(word, one);
			}
		}
	}

	//Reduce过程继承自Reducer<KEYIN,VALUEIN,KEYOUT,VALUEOUT>
	public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();

		// 实现reduce方法 void reduce(KEYIN key, Iterable<VALUEIN> values, Context context)
		// 将map的结果按照key进行汇总到values里面进行reduce操作
		// key、value为map的输出
		// 结果写入context，然后经过OutFormat输出到文件
		public void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	}

	//主程序
	public static void main(String[] args) throws Exception {
		// 创建一个配置
		Configuration conf = new Configuration();
		// 读取命令行参数
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 2) {
			System.err.println("Usage: wordcount <in> <out>");
			System.exit(2);
		}
		//创建一个作业
		Job job = Job.getInstance(conf, "word count");
		//设置工作的类
		job.setJarByClass(WordCount.class);
		//设定Mapper
		job.setMapperClass(TokenizerMapper.class);
		//设定Combiner
		job.setCombinerClass(IntSumReducer.class);
		//设置Reduce
		job.setReducerClass(IntSumReducer.class);
		//设定输出类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		//设置输入输出路径
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		//等待工作完成退出
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}