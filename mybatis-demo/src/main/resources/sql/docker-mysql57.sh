PWD="/Users/foreverlpficloud.com/appdata/mysql"
docker run -p 32000:3306 --name mysql57 -v $PWD/conf:/etc/mysql/conf.d -v $PWD/logs:/logs -v $PWD/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7
#进入容器
docker exec -it mysql57 bash
#登录mysql
mysql -u root -p123456

#修改root登录权限
GRANT ALL ON *.* TO 'root'@'%';
flush privileges;
ALTER USER 'root'@'localhost' IDENTIFIED BY '123456' PASSWORD EXPIRE NEVER;
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
flush privileges;

#修改root密码
update user set authentication_string = password('123456') where user = 'root';
#向root授予所有权限
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '123456' WITH GRANT OPTION;

#添加远程登录用户
CREATE USER 'lipengfei'@'%' IDENTIFIED WITH mysql_native_password BY 'lpflpf';
GRANT ALL PRIVILEGES ON *.* TO 'lipengfei'@'%';


#mysql远程登录
mysql -u root -P 32000 -h 127.0.0.1 -p

#创建数据库

# 通过mysql数据库的user表查看用户相关信息
use mysql;
select host,user,password from user;

#创建数据库
create database testdb default character set utf8 collate utf8_general_ci;
create user 'dba'@'%' identified by 'lpflpf';
grant select,insert,update,delete,create on testdb.* to dba;
FLUSH PRIVILEGES;
grant drop on testdb.* to dba;

#收回权限
# revoke drop on [databasename].* from dba;
# show grants for dba