# 作业

##### 1.描述 Spring 校验注解 org.springframework.validation.annotation.Validated 的工 作原理，它与 Spring Validator 以及 JSR-303 Bean Validation @javax.validation.Valid 之间的关系

- 方法级别

  - `org.springframework.validation.beanvalidation.MethodValidationPostProcessor`在Bean加载的时候，此方法处理器会扫描带有`org.springframework.validation.annotation.Validated`注解的方法，创建切面，由切面对方法参数进行校验

- Validated与Valid有些区别

  - Validated提供分组功能，Valid不支持分组
  - Validated不能用在成员属性上，Valid可以用在成员属性上

- Spring Validator 与Validated或Valid可以实现自定义参数校验

  



