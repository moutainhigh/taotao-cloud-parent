cp ~/mysql-connector-java-8.0.17.jar $HIVE_HOME/lib

schematool -dbType mysql -initSchema

nohup hive --service metastore &
nohup hive --service hiveserver2 &


beeline -i ~/.hiverc -u jdbc:hive2://127.0.0.1:10000 -n root
