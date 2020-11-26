cd /opt/app/taotao-cloud-app/

cp /usr/local/openresty/nginx/conf/mime.type /opt/app/taotao-cloud-app/conf


dnf config-manager --add-repo  https://openresty.org/package/centos/openresty.repo
dnf install openresty

wget https://github.com/doujiang24/lua-resty-kafka/archive/master.zip
unzip master.zip

cp -r ~/lua-resty-kafka-master/lib/resty/kafka /usr/local/openresty/lib/resty/



/usr/local/openresty/nginx/sbin/nginx -p /opt/app/taotao-cloud-collect -c conf/nginx.conf -t
/usr/local/openresty/nginx/sbin/nginx -p /opt/app/taotao-cloud-collect -c conf/nginx.conf
/usr/local/openresty/nginx/sbin/nginx -p /opt/app/taotao-cloud-collect -c conf/nginx.conf -s reload
