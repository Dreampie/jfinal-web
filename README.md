jfinal-web
============

jfinal  web  filter  and  handler，查看其他插件-> [Maven](http://search.maven.org/#search%7Cga%7C1%7Ccn.dreampie)

maven 引用  ${jfinal-web.version}替换为相应的版本如:0.2

```xml
<dependency>
<groupId>cn.dreampie</groupId>
<artifactId>jfinal-web</artifactId>
<version>${jfinal-web.version}</version>
</dependency>
```

支持getModels的的Controller，支持根据ajax返回JsonRender，可以通过returnType=json使后台返回json数据，returnType=default返回action的默认render

```java
class MyController  extends cn.dreampie.web.Controller{

  public void models(){
    List<Model> modes=getModels(Model.class);
  }
}
```

系统异常时，前台需要json的错误信息

```java
//render  json  错误
constants.setErrorRenderFactory(new JsonErrorRenderFactory());
//设置默认的错误也没
constants.setError401View("/view/app/signin.ftl");
constants.setError403View("/view/app/403.ftl");
constants.setError404View("/view/app/404.ftl");
constants.setError500View("/view/app/500.ftl");

```

使用handler让jfinal跳过某些url的过滤，防止xss攻击

```java
//跳过以im开始url
handlers.add(new SkipHandler("/im/**"));
//防xss攻击
handlers.add(new AttackHandler());

```

缓存页面的过滤器CacheFilter和gzip压缩的GzipFilter

```xml

<!-- cache filter-->
<filter>
  <filter-name>cacheFilter</filter-name>
  <filter-class>cn.dreampie.common.web.filter.cache.CacheFilter</filter-class>
</filter>
<filter-mapping>
  <filter-name>cacheFilter</filter-name>
  <url-pattern>/*</url-pattern>
  <dispatcher>REQUEST</dispatcher>
  <dispatcher>FORWARD</dispatcher>
  <dispatcher>INCLUDE</dispatcher>
  <dispatcher>ERROR</dispatcher>
</filter-mapping>
<!--cache filter-->

<!--gzip compress filter-->
<filter>
  <filter-name>gzipFilter</filter-name>
  <filter-class>cn.dreampie.common.web.filter.gzip.GZIPFilter</filter-class>
</filter>
<filter-mapping>
  <filter-name>gzipFilter</filter-name>
  <url-pattern>/*</url-pattern>
  <dispatcher>REQUEST</dispatcher>
  <dispatcher>FORWARD</dispatcher>
  <dispatcher>INCLUDE</dispatcher>
  <dispatcher>ERROR</dispatcher>
</filter-mapping>
<!--gzip compress filter-->

```