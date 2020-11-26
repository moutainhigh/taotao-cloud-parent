dnf config-manager --add-repo  https://openresty.org/package/centos/openresty.repo
dnf install openresty

wget https://github.com/doujiang24/lua-resty-kafka/archive/master.zip
unzip master.zip

cp -r ~/lua-resty-kafka-master/lib/resty/kafka /usr/local/openresty/lib/resty/

mkdir -pv /opt/app/collect-app{conf/vhost,logs/data,script/logs}

cp /usr/local/openresty/nginx/conf/mime.types /opt/app/collect-app/conf
cp /usr/local/openresty/nginx/conf/nginx.conf /opt/app/collect-app/conf

/usr/local/openresty/nginx/sbin/nginx -p /opt/app/collect-app -c conf/nginx.conf -t
/usr/local/openresty/nginx/sbin/nginx -p /opt/app/collect-app -c conf/nginx.conf
/usr/local/openresty/nginx/sbin/nginx -p /opt/app/collect-app -c conf/nginx.conf -s reload

echo "0 23 * * * sh /opt/app/collect-app/script/spilt-access-log.sh >> /opt/app/collect-app/script/logs/spilt-access-log.log 2>&1" > /opt/app/collect-app/script/collect-app-log.cron

crontab /opt/app/collect-app/script/collect-app-log.cron

crontab -l
crontab -e

echo "
.
`-- collect-app
    |-- client_body_temp
    |-- conf
    |   |-- mime.types
    |   |-- nginx.conf
    |   `-- vhost
    |       `-- collect_app.conf
    |-- fastcgi_temp
    |-- logs
    |   |-- access.log
    |   |-- collect-app.access.log
    |   |-- data
    |   |   |-- collect-app.access.1606118347.log
    |   |   `-- collect-app.access.1606119001.log
    |   |-- error.log
    |   |-- nginx.pid
    |   `-- nginx_error.log
    |-- proxy_temp
    |-- scgi_temp
    |-- script
    |   |-- collect-app-log.cron
    |   |-- logs
    |   |   `-- spilt-access-log.log
    |   `-- spilt-access-log.sh
    `-- uwsgi_temp
"

