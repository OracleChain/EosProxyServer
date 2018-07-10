
# About/关于

**PocketEOS-ShieldServer** is the backend server of PocketEOS（blackbox mode）,which is developed by [OracleChain.io](https://oraclechain.io).

**PocketEOS-ShieldServer**是一个PocketEOS的后端服务器（黑盒模式），由[OracleChain团队](https://oraclechain.io)研发。

-------------------------------

# Menu/目录
+ [Overview  简介](#1)
+ [ENVIRONMENT  开发和使用环境](#2)
+ [ExceptionHandling 异常捕获](#3)
+ [ContractErrorCodeSpecification 合约异常规范](#4)
+ [AddYourTokenAsset  对接新币种](#5)
+ [ServerTransaction  自发交易](#6)
+ [About OracleChain  有关欧链](#7)
+ [LICENSE  版权](#8)

------------------------------

<h2 id="1">Overview/简介</h2>

&emsp;&emsp;Pack EOS World in Mobile, Carry Blockchain Age with You！      

&emsp;&emsp;口袋里的EOS世界大门，将区块链生活随身携带！
   
&emsp;&emsp;Now we opensourced our backend server too(apply to blackbox mode).      

&emsp;&emsp;现在，我们把PocketEOS的后端代码也开源出来（适用于黑匣子模式），大家可以自行编译使用。

------------------------------
<h2 id="2">ENVIRONMENT/开发和使用环境</h2>

**Compile ShieldServer from source code/如何从源码编译服务器：**

&emsp;1. prepare a redis server for data cache of token rates

&emsp;2. install IntelliJ IDEA + jdk1.8 + maven 4.0.0

&emsp;3. download our git repository.
>`git clone https://github.com/OracleChain/PocketEOS-ShieldServer.git`

&emsp;4. import the project

&emsp;5. edit redis server configuration in src/main/resources/application.yml.

>`host: redis_server_ip`

>`password: redis_passwd`

&emsp;6. edit server transaction parameters in src/main/java/com/oraclechain/eosio/constants/Variables.java.

>`public static final String eosAccount = "tx_account_name";`

>`public static final String eosPrivateKey = "tx_private_key";`

&emsp;7. Run it.

**Compile PocketEOS client from source code/如何从源码编译PocketEOS客户端：**

&emsp;Android

&emsp;1. install Android Studio 3.0以上 + jdk1.8 +gradle 4.1

&emsp;2. download our git repository.

>`git clone --recurse-submodules https://github.com/OracleChain/PocketEOS-Android.git`

&emsp;MACOS

&emsp;1. XCODE 8.0 compatible

&emsp;2. clone our git repository.

>`git clone --recurse-submodules https://github.com/OracleChain/PocketEOS-IOS.git`

&emsp;3. command + R

**CLIENT DOWNLOAD & TRY/客户端下载试用**

&emsp;[PocketEOS](https://pocketeos.com/)


**DEPENDENCYS/依赖库:**

&emsp;Our C code of ECDSA is based on micro-ecc.And OracleChain build a Publicly Verifiable Secret Sharing on secp256k1 which is published by Schoenmakers on crypto99 conference.

&emsp;C语言版的椭圆曲线算法实现来自micro-ecc，欧链在椭圆曲线secp256k1上实现了Schoenmakers在crypto99上提出的Publicly Verifiable Secret Sharing

&emsp;https://github.com/kmackay/micro-ecc

&emsp;https://github.com/songgeng87/PubliclyVerifiableSecretSharing

&emsp;You can also found it in our chainkit repository

&emsp;你也可以在我们的chainkit库中，找到工具类完整的封装。

&emsp;https://github.com/OracleChain/chainkit

&emsp;Our JAVA code of ECDSA and blockchain utility are based on the source code of [EOSCommander](https://github.com/plactal/EosCommander),thx for the PLACTAL.io team

&emsp;JAVA语言版的椭圆曲线算法及blockchain工具类，来自于[EOSCommander](https://github.com/plactal/EosCommander)，感谢PLACTAL.io为社区的付出。

**Any questions pls join our official Telegram Group below/相关问题反馈，请加欧链官方Telegram群组:**

&emsp;中文群：https://t.me/OracleChainChatCN

&emsp;ENGLISH GROUP：https://t.me/OracleChainChat

------------------------------

<h2 id="3">ExceptionHandling/异常捕获</h2>

### FC Layer Exceptions/FC层异常

### Chain Layer Exceptions/链层异常

------------------------------

<h2 id="4">ContractErrorCodeSpecification 合约异常规范</h2>

### Contract Layer Exceptions Specification/合约异常规范


------------------------------

<h2 id="5">AddYourTokenAsset  对接新币种</h2>

### How to add your own token/对接新币种


------------------------------

<h2 id="6">ServerTransaction  自发交易</h2>

### Make server side transactions/自发交易


------------------------------
<h2 id="7">About OracleChain/有关欧链</h2>

As the world’s first application built on an EOS ecosphere, OracleChain needs to meet the demands of the Oracle (oracle machine) ecosystem by efficiently linking blockchain technology services with various real-life scenarios, thereby delving into this immense tens of billions of dollars valuation market.

OracleChain（欧链）作为全球第一个直面区块链生态Oracle（预言机）需求的基础应用，将区块链技术服务和现实生活中的多种需求场景直接高效对接，深耕这个百亿美金估值的巨大市场。

As a decentralized Oracle technology platform based on the EOS platform, the autonomous Proof-of-Reputation & Deposit mechanism is adopted and used as a fundamental service for other blockchain applications.In addition to Oracle services that provide real-world data to the blockchain, Oracle services that provide cross-chain data are also offered. Given that OracleChain can accomplish the functions of several prediction market applications, such as Augur and Gnosis, OracleChain can also support smart contract businesses that require high-frequency access to outside data in certain scenarios, such as Robo-Advisor.

OracleChain是一个多区块链的去中心化Oracle技术平台，采用自主的PoRD机制，将现实世界数据引入区块链，并将此作为基础设施为其他区块链应用提供服务。
OracleChain将在区块链内提供现实世界数据的Oracle服务，同时还可以提供跨链数据的Oracle服务。基于OracleChain除了能实现Augur、Gnosis等预测市场（Prediction Market）应用的功能之外，还能支撑对链外数据有更高频率访问需求的智能合约业务，比如智能投顾等场景。

OracleChain will nurture and serve those blockchain applications that change the real world. Our mission is to “Link Data, Link World,” with the aim of becoming the infrastructure linking the real world with the blockchain world.

OracleChain将改变当前区块链应用的开发模式，建立全新的生态圈，服务于真正能改变现实世界的区块链应用。

By achieving intra-chain and extra-chain data connectivity, we aspire to create a service provisioning platform that can most efficiently gain access to extra-chain data in the future blockchain world.

OracleChain的使命是“让世界与区块链互联”，立志成为链接现实世界与区块链世界的基础设施，通过把外部数据引入区块链来实现链内链外的数据互通，OracleChian将是未来区块链世界中最高效的获取链外数据的服务提供平台。

<h2 id="8">LICENSE/版权</h2>

**License**

Released under GNU/LGPL Version 3
