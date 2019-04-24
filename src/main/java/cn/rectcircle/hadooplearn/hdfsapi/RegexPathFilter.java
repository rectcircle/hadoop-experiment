package cn.rectcircle.hadooplearn.hdfsapi;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

/*
（7）PathFilter 通过正则过滤文件
 */
public class RegexPathFilter implements PathFilter {
  
  private final String regex;
  private final boolean include;

  public RegexPathFilter(String regex) {
    this(regex, true);
  }
  
  public RegexPathFilter(String regex, boolean include) {
    this.regex = regex;
    this.include = include;
  }

  public boolean accept(Path path) {
    return (path.toString().matches(regex)) ? include : !include;
  }

}
