#!/bin/bash

function start_spark() {
    /opt/spark-3.0.0-bin-hadoop3.2/sbin/start-all.sh
    echo "spark started"
}

function stop_spark() {
    /opt/spark-3.0.0-bin-hadoop3.2/sbin/stop-all.sh
     echo "spark stoped"
}

case $1 in
"start")
    start_spark
    ;;
"stop")
    stop_spark
    ;;
*)
    echo Invalid Args!
    echo 'Usage: '$(basename $0)' start|stop'
    ;;
esac
