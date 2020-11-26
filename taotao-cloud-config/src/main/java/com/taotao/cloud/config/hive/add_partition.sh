#!/bin/bash

exec_date=$1
if [ "${exec_date}" ]; then
    exec_date=`date -d "${exec_date} 1 days ago" +%Y-%m-%d`
else
    exec_date=`date -d "1 days ago" +%Y-%m-%d`
fi

SQL_DATE=${exec_date}

ADD_PARTITION_SQL="
  ALTER TABLE taotao_cloud_access_log DROP IF EXISTS PARTITION (logday = "${exec_date}");
  alter table taotao_cloud_access_log add partition (logday = "${exec_date}") location "/taotao/cloud/access/log/sources/${exec_date}"
"
hive -e "${ADD_PARTITION_SQL}"
