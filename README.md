## 进步社区

## 资料
[es](https://elasticsearch.cn/explore/)  
[Github](https://github.com/xueyu7/community)  
[Bootstrap](https://v3.bootcss.com/)  
[Github OAuth](https://docs.github.com/en/apps/oauth-apps/building-oauth-apps/creating-an-oauth-app)  
[OkHttp](https://square.github.io/okhttp/)  
[Springboot](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)  
[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)  
[Interceptor](https://docs.spring.io/spring-framework/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc)  
[Postman](https://www.postman.com/downloads/)  
[Markdown 插件](http://editor.md.ipandao.com/)  
[Scheduling Tasks](https://spring.io/guides/gs/scheduling-tasks/)  


## 工具
[Visual Paradigm](https://www.visual-paradigm.com)  
[JRebel 热部署](https://blog.csdn.net/yy139926/article/details/125655925)

## 脚本
```sql
CREATE TABLE USER
(
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(100),
  `name` varchar(50),
  `token` char(36),
  `avatar_url` varchar(255),
  `gmt_create` bigint(0),
  `gmt_modified` bigint(0),
  PRIMARY KEY (`id`)
);
CREATE TABLE QUESTION
(
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(50),
  `description` text,
  `tag` varchar(255),
  `creator` int(0), 
  `view_count` int(0) DEFAULT 0,
  `comment_count` int(0) DEFAULT 0,
  `like_count` int(0) DEFAULT 0,
  `gmt_create` bigint(0),
  `gmt_modified` bigint(0),  
  PRIMARY KEY (`id`)
);
CREATE TABLE COMMENT
(
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `parent_id` int(0) NOT NULL COMMENT '父类id',
  `type` int(0) NOT NULL COMMENT '父类类型',
  `commentator` int(0) NOT NULL COMMENT '评论人id',
  `content` varchar(1024) NOT NULL COMMENT '评论内容',
  `like_count` int(10) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '点赞数',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
```


