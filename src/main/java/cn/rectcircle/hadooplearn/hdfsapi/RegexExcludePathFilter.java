package cn.rectcircle.hadooplearn.hdfsapi;

// cc RegexExcludePathFilter A PathFilter for excluding paths that match a regular expression
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

// vv RegexExcludePathFilter
/*
  （7）PathFilter 通过正则排除文件
*/
public class RegexExcludePathFilter implements PathFilter {
  
  private final String regex;

  public RegexExcludePathFilter(String regex) {
    this.regex = regex;
  }

  public boolean accept(Path path) {
    return !path.toString().matches(regex);
  }
}
// ^^ RegexExcludePathFilter