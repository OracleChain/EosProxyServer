
# 关于

**PocketEOS-ShieldServer**是一个PocketEOS的后端服务器（黑盒模式），由[OracleChain团队](https://oraclechain.io)研发。

-------------------------------

# 目录
+ [简介](#1)
+ [开发和使用环境](#2)
+ [异常捕获](#3)
+ [合约异常规范](#4)
+ [对接新币种](#5)
+ [自发交易](#6)
+ [有关欧链](#7)
+ [版权](#8)

------------------------------

<h2 id="1">简介</h2>      

&emsp;&emsp;口袋里的EOS世界大门，将区块链生活随身携带！ 

&emsp;&emsp;现在，我们把PocketEOS的后端代码也开源出来（适用于黑匣子模式），大家可以自行编译使用。

------------------------------
<h2 id="2">开发和使用环境</h2>

**如何从源码编译服务器：**

&emsp;1. prepare a redis server for data cache of token rates

&emsp;2. install IntelliJ IDEA + jdk1.8 + maven 4.0.0

&emsp;3. download our git repository.
>`git clone https://github.com/OracleChain/PocketEOS-ShieldServer.git`

&emsp;4. import the project with IntelliJ IDEA

&emsp;5. edit redis server configuration in src/main/resources/application.yml.

>`host: redis_server_ip`

>`password: redis_passwd`

&emsp;6. edit server transaction parameters in src/main/java/com/oraclechain/eosio/constants/Variables.java.

>`public static final String eosAccount = "tx_account_name";`

>`public static final String eosPrivateKey = "tx_private_key";`

&emsp;7. Run it.

**如何从源码编译PocketEOS客户端：**

&emsp;Android

&emsp;1. install Android Studio 3.0以上 + jdk1.8 +gradle 4.1

&emsp;2. clone our git repository.

>`git clone --recurse-submodules https://github.com/OracleChain/PocketEOS-Android.git`

&emsp;3. change the configuration of backend server address according to your local PocketEOS-ShieldServer.

&emsp;4. import the project and run it.

&emsp;MACOS

&emsp;1. install XCODE 8.0

&emsp;2. clone our git repository:

>`git clone --recurse-submodules https://github.com/OracleChain/PocketEOS-IOS.git`

&emsp;3. change the configuration of backend server address according to your local PocketEOS-ShieldServer.

&emsp;4. import the project and run `command + R`

**客户端下载试用**

&emsp;[PocketEOS](https://pocketeos.com/)


**依赖库:**

&emsp;C语言版的椭圆曲线算法实现来自micro-ecc，欧链在椭圆曲线secp256k1上实现了Schoenmakers在crypto99上提出的Publicly Verifiable Secret Sharing

&emsp;https://github.com/kmackay/micro-ecc

&emsp;https://github.com/songgeng87/PubliclyVerifiableSecretSharing

&emsp;你也可以在我们的chainkit库中，找到工具类完整的封装。

&emsp;https://github.com/OracleChain/chainkit

&emsp;JAVA语言版的椭圆曲线算法及blockchain工具类，来自于[EOSCommander](https://github.com/plactal/EosCommander)，感谢PLACTAL.io为社区的付出。

**相关问题反馈，请加欧链官方Telegram群组:**

&emsp;中文群：https://t.me/OracleChainChatCN

&emsp;ENGLISH GROUP：https://t.me/OracleChainChat

------------------------------

<h2 id="3">异常捕获</h2>

### FC层异常

### 链层异常

------------------------------

<h2 id="4">合约异常规范</h2>

### 合约异常规范


------------------------------

<h2 id="5">对接新币种</h2>

### 对接新币种


------------------------------

<h2 id="6">自发交易</h2>

### 自发交易


------------------------------
<h2 id="7">有关欧链</h2>

OracleChain（欧链）作为全球第一个直面区块链生态Oracle（预言机）需求的基础应用，将区块链技术服务和现实生活中的多种需求场景直接高效对接，深耕这个百亿美金估值的巨大市场。

OracleChain是一个多区块链的去中心化Oracle技术平台，采用自主的PoRD机制，将现实世界数据引入区块链，并将此作为基础设施为其他区块链应用提供服务。
OracleChain将在区块链内提供现实世界数据的Oracle服务，同时还可以提供跨链数据的Oracle服务。基于OracleChain除了能实现Augur、Gnosis等预测市场（Prediction Market）应用的功能之外，还能支撑对链外数据有更高频率访问需求的智能合约业务，比如智能投顾等场景。

OracleChain将改变当前区块链应用的开发模式，建立全新的生态圈，服务于真正能改变现实世界的区块链应用。

OracleChain的使命是“让世界与区块链互联”，立志成为链接现实世界与区块链世界的基础设施，通过把外部数据引入区块链来实现链内链外的数据互通，OracleChian将是未来区块链世界中最高效的获取链外数据的服务提供平台。

<h2 id="8">版权</h2>

**License**

Released under GNU/LGPL Version 3
