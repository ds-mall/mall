# dev环境启动过程

## 1 启动mysql
```
docker run --name mall -v "$PWD/db_data":/var/lib/mysql -e MYSQL_DATABASE=’mall-dev‘ -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:5.7
```

## 2 启动redis
```
docker run --name redis_default -p 6379:6379 -d redis
```

## 3 启动tomcat
```
apache-tomcat-8.5.53/bin/startup.sh
```

## 4 启动后端

## 5 打开前端
```
http://localhost:8080/mall-shop/index.html
```