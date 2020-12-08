#修改以下内容
export JAVA_HOME=/opt/module/Java/jdk1.8.0_212
export HADOOP_HOME=/opt/module/Hadoop/hadoop-3.1.3
export PATH=$PATH:${HADOOP_HOME}/bin
export HADOOP_OPTS="-Djava.library.path=${HADOOP_HOME}/lib/native"
export HADOOP_LOG_DIR=${HADOOP_HOME}/logs
#PID存放目录，若没有此配置则默认存放在tmp临时文件夹中，在启动和关闭HDFS时可能会报错
export HADOOP_PID_DIR=${HADOOP_HOME}/pids
#先注释掉，有问题可以打开，将调试信息打印在console上
#export HADOOP_ROOT_LOGGER=DEBUG,console
