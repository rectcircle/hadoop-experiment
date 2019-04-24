package cn.rectcircle.hadooplearn.ioapi;

// cc TextIterator Iterating over the characters in a Text object
import java.nio.ByteBuffer;

import org.apache.hadoop.io.Text;

// vv TextIterator
/*
  Hadoop 序列化非常简单，没有版本和类型等附加信息，仅仅是将底层表示的值转化为字节表示
  所以非常紧凑
  （3） 对Text中的Unicode字符进行遍历
 */
public class TextIterator {
  
  public static void main(String[] args) {    
    Text t = new Text("\u0041\u00DF\u6771\uD801\uDC00");
    
    ByteBuffer buf = ByteBuffer.wrap(t.getBytes(), 0, t.getLength());
    int cp;
    while (buf.hasRemaining() && (cp = Text.bytesToCodePoint(buf)) != -1) {
      System.out.println(Integer.toHexString(cp));
    }
  }  
}
// ^^ TextIterator
