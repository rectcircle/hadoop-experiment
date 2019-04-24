#!/usr/bin/env python
# -*- coding: utf-8 -*-

import re
import sys

# Python 版的stream处理、基于stdio，
# 可以考虑使用https://github.com/klbostee/dumbo/wiki/Short-tutorial
# 测试方式：
"""
cat input/ncdc/sample.txt | \
src/main/java/cn/rectcircle/hadooplearn/mrintro/stream/python/max_temperature_map.py | \
sort | \
src/main/java/cn/rectcircle/hadooplearn/mrintro/stream/python/max_temperature_reduce.py
"""
# 在集群中运行
"""
hadoop jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-*.jar \
  -input input/ncdc/sample.txt \
  -output output \
  -mapper src/main/java/cn/rectcircle/hadooplearn/mrintro/stream/python/max_temperature_map.py \
  -combiner src/main/java/cn/rectcircle/hadooplearn/mrintro/stream/python/max_temperature_reduce.py \
  -reducer src/main/java/cn/rectcircle/hadooplearn/mrintro/stream/python/max_temperature_reduce.py
  # 分布式环境下需要将制定files
  # -files src/main/java/cn/rectcircle/hadooplearn/mrintro/stream/python/max_temperature_map.py, \
  #        src/main/java/cn/rectcircle/hadooplearn/mrintro/stream/python/max_temperature_reduce.py \
"""
for line in sys.stdin:
  val = line.strip()
  (year, temp, q) = (val[15:19], val[87:92], val[92:93])
  if (temp != "+9999" and re.match("[01459]", q)):
    print "%s\t%s" % (year, temp)
