# 第一周作业

参考
com.salesmanager.shop.tags.CommonResponseHeadersTag 实现一个自定义的 Tag，将 Hard Code 的 Header 名值对，变为属性配置的方式，类似于:

```
<tag> 
  <name>common-response-headers</name> 
  <tag-class>com.salesmanager.shop.tags.CommonResponseH eadersTag</tag-class>
  <body-content>empty</body-content>
  <attribute>
    <name>Cache-Control</name> 
    <required>false</required> 
    <rtexprvalue>no-cache</rtexprvalue> 
    <type>java.lang.String</type>
  </attribute>
  <attribute>
    <name>Pragma</name>
    <required>false</required>
    <rtexprvalue>no-cache</rtexprvalue> 
    <type>java.lang.String</type>
  </attribute>
  <attribute>
    <name>Expires</name> 
    <required>false</required> 
    <rtexprvalue>-1</rtexprvalue> 
    <type>java.lang.Long</type>
  </attribute>
</tag>
```

1. 实现自定义标签
2. 编写自定义标签 tld 文件 
3.  部署到 Servlet 应用

**不要求整合到当前，可以做一个 demo 项目**
