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
title = 科洛的服务器
#页面主标题
header-title = 科洛的服务器
#页面副标题
header-subtitle = 用MachineStatus来偷窥服务器们的网站~~
#网站icon地址
icon = https://file.korostudio.cn/alphaillust_68988937_20181210_114051_1617519897520.png@s_0,w_512,l_1,f_png,d_progressive,q_50
#自定义fotter，如不了解可删去
footer = <br/>
```
