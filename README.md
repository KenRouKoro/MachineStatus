# MachineStatus
### MachineStatus服务器监控，一个开箱即用的服务器监控系统。
## 如何使用
### 1.依赖检查
目前运行 MachineStatus 的最低依赖要求为 JRE 16，请务必确保在进行下面操作之前已经正确安装了 JRE。

目前介绍两种 Linux 发行版的安装方式，均为 OpenJRE。(推荐使用JDK17)

#### CentOS 
``` shell
sudo yum install java-latest-openjdk -y
```
检查版本：
``` shell
java -version
```
如果输出以下类似内容即代表成功
``` shell
openjdk version "17" 2021-09-14
```
#### Ubuntu 
``` shell
sudo apt-get install openjdk-latest-jre -y
```
#### 检查版本：
``` shell
java -version
```
如果输出以下类似内容即代表成功
``` shell
openjdk version "17" 2021-09-14
```
### 2.建立目录结构
**Windows：**
**建立C://Users/${汝的用户名}/.ms/server 文件夹**
**在该目录下新建配置文件 setting.setting**
**内容参考以下代码**
``` yml
#网页标题
title = MachineStatus
#页面主标题
header-title = Machine Status
#页面副标题
header-subtitle = 用MachineStatus来偷窥服务器们的网站~~
#网站icon地址，需要加上http://或者https://
icon = https://file.korostudio.cn/alphaillust_68988937_20181210_114051_1617519897520.png@s_0,w_512,l_1,f_png,d_progressive,q_50
#自定义fotter，如不了解可删去，注意！部分字符需要转义。
footer = 
```
****
**Linux**
创建存放 运行包 的目录，这里以 ~/app 为例
``` shell
mkdir ~/app && cd ~/app
```
下载运行包
``` shell
wget https://github.com/KenRouKoro/MachineStatus/releases/download/0.2-Alpha/MSS-0.0.2-Alpha.jar -O MSS.jar
```
下载示例Spring配置文件
``` shell
wget https://file.korostudio.cn/application_1635071908350.yml -O ./application.yaml 
```
编辑Spring配置文件，配置数据库账户或者端口等
``` shell
vim application.yaml
```
创建 工作目录
``` shell
mkdir ~/.ms/server && cd ~/.ms/server
```
下载示例配置文件到工作目录
``` shell
wget https://file.korostudio.cn/simple_setting_1635069866273.setting -O ./setting.setting
```
编辑配置文件，配置自定义数据，请按照注释提示填写
``` shell
vim setting.setting
```
### 3.测试运行 MachineStatus
``` shell
cd ~/app && java -jar MSS.jar
```
如看到有类似以下日志输出，则代表启动成功。
``` log
[2021-10-24 18:46:46.739] [main] [INFO ] [org.apache.coyote.http11.Http11NioProtocol] - Starting ProtocolHandler ["http-nio-3620"]
```
打开 http://ip: 端口号 即可看到监控界面
## 4.作为服务运行
1.下载 MachineStatus 官方的 mss.service 模板
``` shell
wget  https://file.korostudio.cn/mss_1635084422897.service -O /etc/systemd/system/mss.service
```
2.修改 mss.service
```shell
vim /etc/systemd/system/mss.service
```
3.修改配置
YOUR_JAR_PATH：MachineStatus 运行包的绝对路径，例如 /root/app/MSS.jar，注意：此路径不支持 ~ 符号。
```
[Unit]
Description=MSS Service
Documentation=https://github.com/KenRouKoro/MachineStatus
After=network-online.target
Wants=network-online.target

[Service]
Type=simple
ExecStart=/usr/bin/java -server -Xms256m -Xmx256m -jar YOUR_JAR_PATH
ExecStop=/bin/kill -s QUIT $MAINPID
Restart=always
StandOutput=syslog

StandError=inherit

[Install]
WantedBy=multi-user.target
```
4.重新加载 systemd
```shell
systemctl daemon-reload
```
5.运行服务
```shell
systemctl start mss
```
6.在系统启动时启动服务
```shell
systemctl enable mss
```
您可以查看服务日志检查启动状态
```shell
journalctl -n 20 -u mss
```