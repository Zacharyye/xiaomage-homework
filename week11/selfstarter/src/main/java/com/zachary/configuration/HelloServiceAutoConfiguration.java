package com.zachary.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.zachary.property.HelloServiceProperties;

/**
 * @Configuration 表明此类是一个配置类，将变为一个bean被Spring进行管理
 * @EnableConfigurationProperties 启用属性配置，将读取HelloServiceProperties里面的属性
 * @ConditionalOnClass 当类路径下面有HelloServiceConfiguration此类时，自动配置
 * @ConditionalOnProperty 判断指定的属性是否具备指定的值
 * @ConditionalOnMissingBean 当容器中没有指定bean时，创建此bean。
 */
@Configuration
@EnableConfigurationProperties(HelloServiceProperties.class)
@ConditionalOnClass(HelloServiceConfiguration.class)
@ConditionalOnProperty(prefix = "com.zachary", value = "enabled", matchIfMissing = true)
public class HelloServiceAutoConfiguration {
  @Autowired
  private HelloServiceProperties helloServiceProperties;

  @Bean
  @ConditionalOnMissingBean(HelloServiceConfiguration.class)
  public HelloServiceConfiguration helloServiceConfiguration() {
    HelloServiceConfiguration helloService = new HelloServiceConfiguration();
    helloService.setName(helloServiceProperties.getName());
    helloService.setHobby(helloServiceProperties.getHobby());
    return helloService;
  }
}
