package com.zachary.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ConfigurationProperties 配置此注解可以自动导入application.properties配置文件中的属性，前提需要指定属性前缀prefix。
 * 如果application.properties文件中未指定相应属性，便使用默认的
 */
@ConfigurationProperties(prefix = "com.zachary")
public class HelloServiceProperties {
  private String name = "zachary";

  private String hobby = "bodybuilding";

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHobby() {
    return hobby;
  }

  public void setHobby(String hobby) {
    this.hobby = hobby;
  }
}
