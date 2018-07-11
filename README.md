[中文版](https://github.com/OracleChain/PocketEOS-ShieldServer/blob/master/README-cn.md)

# About

**PocketEOS-ShieldServer** is the backend server of PocketEOS(blackbox mode),which is developed by [OracleChain.io](https://oraclechain.io).

-------------------------------

# Menu
+ [Overview](#1)
+ [ENVIRONMENT](#2)
+ [Exception Handling](#3)
+ [Contract Error Code Specification](#4)
+ [Add Your Token Asset](#5)
+ [Server Transaction](#6)
+ [About OracleChain](#7)
+ [LICENSE](#8)

------------------------------

<h2 id="1">Overview</h2>

&emsp;&emsp;Pack EOS World in Mobile, Carry Blockchain Age with You！  
   
&emsp;&emsp;Now we opensourced our backend server too(apply to blackbox mode).      

------------------------------
<h2 id="2">ENVIRONMENT</h2>

**Compile ShieldServer from source code：**

&emsp;1. prepare a redis server for data cache of token rates

&emsp;2. install IntelliJ IDEA + jdk1.8 + maven 4.0.0

&emsp;3. clone our git repository.
>`git clone https://github.com/OracleChain/PocketEOS-ShieldServer.git`

&emsp;4. import the project with IntelliJ IDEA

&emsp;5. edit redis server configuration in src/main/resources/application.yml.

>`host: redis_server_ip`

>`port: 6379`

>`password: redis_passwd`

&emsp;6. edit server transaction parameters in src/main/java/com/oraclechain/eosio/constants/Variables.java.

>`public static final String eosAccount = "tx_account_name";`

>`public static final String eosPrivateKey = "tx_private_key";`

&emsp;7. Run it.

**Compile PocketEOS client from source code：**

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

**CLIENT DOWNLOAD & TRY**

&emsp;[PocketEOS](https://pocketeos.com/)


**DEPENDENCYS:**

&emsp;Our C code of ECDSA is based on micro-ecc.And OracleChain build a Publicly Verifiable Secret Sharing on secp256k1 which is published by Schoenmakers on crypto99 conference.

&emsp;https://github.com/kmackay/micro-ecc

&emsp;https://github.com/songgeng87/PubliclyVerifiableSecretSharing

&emsp;You can also found it in our chainkit repository

&emsp;https://github.com/OracleChain/chainkit

&emsp;Our JAVA code of ECDSA and blockchain utility are based on the source code of [EOSCommander](https://github.com/plactal/EosCommander),thx for the PLACTAL.io team

**Any questions pls join our official Telegram Group below:**

&emsp;中文群：https://t.me/OracleChainChatCN

&emsp;ENGLISH GROUP：https://t.me/OracleChainChat

------------------------------

<h2 id="3">Exception Handling</h2>

### FC Layer Exceptions

### Chain Layer Exceptions

------------------------------

<h2 id="4">Contract Error Code Specification</h2>

### Contract Layer Exceptions Specification


------------------------------

<h2 id="5">Add Your Token Asset</h2>

### How to add your own token


------------------------------

<h2 id="6">Server Transaction</h2>

### Make server side transactions


------------------------------
<h2 id="7">About OracleChain</h2>

As the world’s first application built on an EOS ecosphere, OracleChain needs to meet the demands of the Oracle (oracle machine) ecosystem by efficiently linking blockchain technology services with various real-life scenarios, thereby delving into this immense tens of billions of dollars valuation market.

As a decentralized Oracle technology platform based on the EOS platform, the autonomous Proof-of-Reputation & Deposit mechanism is adopted and used as a fundamental service for other blockchain applications.In addition to Oracle services that provide real-world data to the blockchain, Oracle services that provide cross-chain data are also offered. Given that OracleChain can accomplish the functions of several prediction market applications, such as Augur and Gnosis, OracleChain can also support smart contract businesses that require high-frequency access to outside data in certain scenarios, such as Robo-Advisor.

OracleChain will nurture and serve those blockchain applications that change the real world. Our mission is to “Link Data, Link World,” with the aim of becoming the infrastructure linking the real world with the blockchain world.

By achieving intra-chain and extra-chain data connectivity, we aspire to create a service provisioning platform that can most efficiently gain access to extra-chain data in the future blockchain world.


<h2 id="8">LICENSE</h2>

**License**

Released under GNU/LGPL Version 3
