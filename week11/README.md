##### **作业**

利用 Spring Boot 自动装配特性，编写一个自定义 Starter， 规则如下:

利用 @EnableAutoConfiguration 加载一个自定义 Confugration 类

Configuration 类装配条件需要它非 Web 应用 WebApplicationType = NONE

Configuration 类中存在一个 @Bean 返回一个输出 HelloWorld ApplicationRunner 对象

> 启动类：https://github.com/Zacharyye/xiaomage-homework/blob/main/week11/starter/src/main/java/com/zachary/starter/StarterApplication.java

