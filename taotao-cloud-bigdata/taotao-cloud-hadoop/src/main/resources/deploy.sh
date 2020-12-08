
hdfs namenode -format

start-dfs.sh

start-yarn.sh

hadoop-httpfs start

hadoop fs -mkdir -p /tmp
hadoop fs -chmod 777 /tmp
hadoop fs -mkdir -p /user/hive/warehouse
hadoop fs -chmod 777 /user/hive/warehouse
hadoop fs -mkdir -p /user/hue
hadoop fs -chmod 777 /user/hue

wget http://www.congiu.net/hive-json-serde/1.3.8/hdp23/json-serde-1.3.8-jar-with-dependencies.jar
wget http://www.congiu.net/hive-json-serde/1.3.8/hdp23/json-udf-1.3.8-jar-with-dependencies.jar

hadoop fs -mkdir -p /common/lib
hadoop fs -chmod 777 /common/lib
hadoop dfs -put -f json-serde-1.3.8-jar-with-dependencies.jar /common/lib
hadoop dfs -put -f json-udf-1.3.8-jar-with-dependencies.jar /common/lib
