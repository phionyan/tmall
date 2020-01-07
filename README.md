# 基于SpringBoot模仿天猫整站 

## 简介
Java后端学完了，总觉得应该总结下，对程序员来说，最好的总结就是实现一遍。

参考网上类似项目的实现，于是有了这个项目，计划分为三个阶段：

1. 实现天猫的后台管理和前台购物等商城核心功能。----------进行中:fire:

2. 升级系统为分布式的商城，实现双十一秒杀等需要高并发的功能。

3. 完善系统细节，使其接近现实生产环境（预想，有点难:astonished:）。


### 架构图（截止2019-12-3）


## 进度及短期目标

:full_moon:代表已完成
:new_moon:代表未完成

### 阶段一:

* 系统与数据库设计，环境搭建  :full_moon:
* 基本框架实现，Hello World  :full_moon:
* 分类管理界面及CRUD         :full_moon:
* 属性管理界面及CRUD         :full_moon:
* 产品管理界面及CRUD         :full_moon:
* 用户管理界面及CRUD         :full_moon:
* 订单管理界面及CRUD         :full_moon:
* 前端                      :full_moon:
截止至1月17日，前后端基本完成。
### 阶段二:

### 阶段三:


## 相关技术

### 技术栈

目前技术栈：
后端：SpringBoot+jpa+mysql
前端：Thymeleaf+vue.js

###  数据库表
数据共九张表 分别为:
> user：用户表

> category：分类表，如大衣，冰箱

> product：产品表，每个分类有多个产品

> productImage:产品图片表，图片放在数据库中，方便迁移

> property：属性表，存放属性信息，如颜色，重量，品牌，厂商，型号等

> propertyvalue：属性值表，红色，90kg，海尔等等

> order：订单表

> orderitem：订单项表，一个订单有多个订单项

> review:评价表，用户在商品下的评价
