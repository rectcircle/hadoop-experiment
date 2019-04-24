package cn.rectcircle.hadooplearn.wordcount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.StringUtils;

public class WordCount2 {

	public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

		static enum CountersEnum {
			INPUT_WORDS
		}

		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		private boolean caseSensitive;
		private Set<String> patternsToSkip = new HashSet<String>();

		private Configuration conf;
		private BufferedReader fis;

		// 启动map之前执行的操作
		@Override
		public void setup(Context context) throws IOException, InterruptedException {
			conf = context.getConfiguration();
			//获取到配置
			caseSensitive = conf.getBoolean("wordcount.case.sensitive", true);
			if (conf.getBoolean("wordcount.skip.patterns", false)) {
				URI[] patternsURIs = Job.getInstance(conf).getCacheFiles();
				for (URI patternsURI : patternsURIs) {
					Path patternsPath = new Path(patternsURI.getPath());
					String patternsFileName = patternsPath.getName().toString();
					parseSkipFile(patternsFileName);
				}
			}
		}

		//解析skip文件
		private void parseSkipFile(String fileName) {
			try {
				fis = new BufferedReader(new FileReader(fileName));
				String pattern = null;
				while ((pattern = fis.readLine()) != null) {
					patternsToSkip.add(pattern);
				}
			} catch (IOException ioe) {
				System.err.println(
						"Caught exception while parsing the cached file '" + StringUtils.stringifyException(ioe));
			}
		}

		@Override
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			//将匹配部分删除
			String line = (caseSensitive) ? value.toString() : value.toString().toLowerCase();
			for (String pattern : patternsToSkip) {
				line = line.replaceAll(pattern, "");
			}
			//Map逻辑
			StringTokenizer itr = new StringTokenizer(line);
			while (itr.hasMoreTokens()) {
				word.set(itr.nextToken());
				context.write(word, one);
				// 计数器对象，将会在执行过程中输出
				Counter counter = context.getCounter(CountersEnum.class.getName(), CountersEnum.INPUT_WORDS.toString());
				counter.increment(1);
			}
		}
	}

	public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();

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

	public static void main(String[] args) throws Exception {
		//创建一个配置
		Configuration conf = new Configuration();
		//创建一个命令行选项解析器
		GenericOptionsParser optionParser = new GenericOptionsParser(conf, args);
		String[] remainingArgs = optionParser.getRemainingArgs();
		if ((remainingArgs.length != 2) && (remainingArgs.length != 4)) {
			System.err.println("Usage: wordcount <in> <out> [-skip skipPatternFile]");
			System.exit(2);
		}
		//创建一个Job
		Job job = Job.getInstance(conf, "word count");
		//通过查找给定类的来源来设置Jar。
		job.setJarByClass(WordCount2.class);
		//设置Mapper
		job.setMapperClass(TokenizerMapper.class);
		//设定Combiner
		job.setCombinerClass(IntSumReducer.class);
		//设定Reduce
		job.setReducerClass(IntSumReducer.class);
		//设定输出类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		//查找skip参数
		List<String> otherArgs = new ArrayList<String>();
		for (int i = 0; i < remainingArgs.length; ++i) {
			if ("-skip".equals(remainingArgs[i])) {
				//如果存在skip参数
				//将该路径加入Job缓存文件
				job.addCacheFile(new Path(remainingArgs[++i]).toUri());
				//并设置Job配置
				job.getConfiguration().setBoolean("wordcount.skip.patterns", true);
			} else {
				otherArgs.add(remainingArgs[i]);
			}
		}
		//设置输入输出路径
		FileInputFormat.addInputPath(job, new Path(otherArgs.get(0)));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs.get(1)));
		//启动Job并等待完成并退出
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}