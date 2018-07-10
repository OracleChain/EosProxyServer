
# About/关于

**PocketEOS-ShieldServer** is an backend of PocketEOS（blackbox mode）,which is developed by [OracleChain.io](https://oraclechain.io).

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
   
&emsp;&emsp;EOS Wallet Easy to Use: Pocket EOS fully focuses on users’ demands and habits, provides an advanced wallet tool with refinement and accessibility. There are no learning cost even for a beginner of blockchain. Just use Pocket EOS and explore the charming EOS!     

&emsp;&emsp;简单、易用的EOS钱包：Pocket EOS充分考虑用户使用需求和使用习惯，将接入区块链网络的钱包工具打磨得精致而易用，即使没有相关经验的手机用户，也能通过平滑流畅的引导，体验到区块链世界的魅力。   

&emsp;&emsp;Powerful and Temperate Social Functions: Pocket EOS tries to offer a balance point between Interactivity and self controlling for blockchain lives. You could send crypto-red-packets to your friends in WeChat or QQ with the minimal personal information revealed.       

&emsp;&emsp;强大而节制的社交功能：Pocket EOS相信，区块链的魅力在于分享与自主之间的平衡。通过Pocket EOS，您在给微信/QQ好友发送数字资产红包、享受全新社交乐趣的同时，也不用担心个人信息的泄露。

&emsp;&emsp;Keep Your Asset Secret: Pocket EOS makes every effort to protect your privacy. The specially built Black Box Mode allows your remain anonymous by the maximum limitation. You could hold your EOS quite personally.      

&emsp;&emsp;注重您的资产隐私：Pocket EOS知道，您的每一分钱都是辛苦奋斗后的小秘密。您可以通过Pocket EOS专门打造的黑盒子模式，在区块链网络支持的最大限度内保持资产的匿名性，做个安安静静的EOSer。
    
&emsp;&emsp;Colour Dapps: Besides an excellent EOS wallet, Pocket EOS is a gateway for qualitied EOS applications. You could “Get Token Answered”, or play amusing games. More developers will come with their Dapps, waiting for your discovering!     

&emsp;&emsp;丰富多彩的Dapps：Pocket EOS不仅仅是优秀的EOS钱包，更是优秀EOS Dapp的聚集地。您能在这里“有问币答”，也能玩到有趣的去中心化游戏。更多开发者正带着他们的产品到来，只能您的发现！  


&emsp;&emsp;Open Source to All, Supports from Global: You could read the core code for Pocket EOS in Github now. EOS developer all over the world is helping us to make it safer and more reliable for you.     

&emsp;&emsp;开源代码，全球支持：Pocket EOS的代码已经在Github上公开，接受全球EOS开发者的审计和修改建议，专为您提供最安全、最可靠的EOS工具。

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

&emsp;our ECDSA and blockchain utility are based on the source code of [EOSCommander](https://github.com/plactal/EosCommander),thx for the PLACTAL.io team

&emsp;我们的椭圆曲线算法及blockchain工具类，来自于[EOSCommander](https://github.com/plactal/EosCommander)，感谢PLACTAL.io为社区的付出。


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
