# 基于SpringBoot模仿天猫整站

目前技术栈：
后端：SpringBoot+jpa+mysql
前端：Thymeleaf+vue.js

-------------------------------
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
