# Spring Boot 3 + Redis + Kafka 秒杀系统

## 1. 项目介绍

基于Spring Boot 3实现的高并发秒杀系统,采用Redis预减库存 + Kafka异步处理订单的架构设计。

### 1.1 技术栈

- Spring Boot 3.0
- Redis 缓存
- Kafka 消息队列
- MySQL 数据库
- JUnit 5 测试框架
- Lombok

### 1.2 核心功能

- 商品秒杀
- 订单异步处理
- 防止超卖
- 限流控制

## 2. 系统架构

### 2.1 整体设计

秒杀核心实现: