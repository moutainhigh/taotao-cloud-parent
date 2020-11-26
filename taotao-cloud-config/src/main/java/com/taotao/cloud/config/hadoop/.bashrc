export JAVA_HOME="/opt/jdk1.8.0_131"
export PATH=$PATH:$JAVA_HOME/bin

export HADOOP_HOME="/opt/hadoop-3.2.1"
export HADOOP_USER="root"
export HADOOP_MAPRED_HOME=$HADOOP_HOME
export HADOOP_COMMON_HOME=$HADOOP_HOME
export HADOOP_HDFS_HOME=$HADOOP_HOME
export HADOOP_LIBEXEC_HOME=$HADOOP_HOME/libexec

export HDFS_NAMENODE_USER="root"
export HDFS_DATANODE_USER="root"
export HDFS_SECONDARYNAMENODE_USER="root"

export YARN_HOME=$HADOOP_HOME
export YARN_RESOURCEMANAGER_USER="root"
export YARN_NODEMANAGER_USER="root"

export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin


