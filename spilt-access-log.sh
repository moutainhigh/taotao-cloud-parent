#/bin/bash

# filename: spilt-access-log.sh
# desc: 切分nginx的access log到指定目录下 flume进行采集
# date: 2020-11-23

# 帮助
usage(){
  echo "usage"
  echo "spilt-access-log.sh [-f log_file] [-d data_dir] [-p pid_file]"
  echo "description"
  echo "log_file: nginx access file absolute path"
  echo "data_dir: spilt data dir"
  echo "pid_file: nginx pid file absolute path"
  echo "Waning: if no params use default"
  exit 1
}

default(){
  echo "use default"
  echo "log_file=/opt/app/collect-app/logs/collect-app.access.log"
  echo "data_dir=/opt/app/collect-app/logs/data/"
  echo "pid_file=/opt/app/collect-app/logs/nginx.pid"

  log_file="/opt/app/collect-app/logs/collect-app.access.log"
  data_dir="/opt/app/collect-app/logs/data/"
  pid_file="/opt/app/collect-app/logs/nginx.pid"
}

while getopts 'f:d:p:h' OPT; do
    case $OPT in
    f) log_file="$OPTARG";;
    d) log_file="$OPTARG";;
    p) log_file="$OPTARG";;

    h) usage;;
    ?) usage;;
    *) usage;;
    esac
done

if [ $# -eq 0 ]; then
    default
fi

if [ ! "${log_file}" ] || [ ! "${data_dir}" ] || [ ! ${pid_file} ]; then
    echo "params is empty"
    exit -1
fi

line = `tail -n 1 ${log_file}`
if [ ! "${line}" ]; then
    echo "access log no data no spilt"
    exit 0
fi

echo collect-app.access.$(date +%s).log

kill -USR1 `cat ${pid_file}`

echo "finish"

# echo "*/5 * * * * sh /opt/app/collect-app/script/spilt-access-log.sh >> /opt/app/collect-app/script/logs/spilt-access-log.log 2>&1" > /opt/app/collect-app/script/collect-app-log.cron
