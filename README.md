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
```
public Render getRender() {
    if (ReturnKit.isJson(getRequest()) && !(ReturnKit.isJson(super.getRender()))) {
      return renderFactory.getJsonRender();
    }
    return super.getRender();
  }
  
```
获取对象数组
```html

<input type="text" name="model[0].name" value="0"/>
<input type="text" name="model[1].name" value="1"/>
<input type="text" name="model[2].name" value="2"/>

```
```java
class MyController  extends cn.dreampie.web.Controller{

  public void models(){
    List<Model> modes=getModels(Model.class);
  }
}
```

封装好的Model类cn.dreampie.web.model.Model,推荐你的所有的model继承它
```java
model.getJson() //获取json字符串
model.findAll()  //查询全部数据
model.findBy(String where, Object... paras)//根据where条件查询全部数据 如：List<User> users = User.dao.findBy(" `user`.deleted_at is null", loginName); `user` 数据库的别名 `modelName首字母小写`  `符号避免关键字
model.findTopBy(int topNumber, String where, Object... paras)//查询前几条
model.findFirstBy(String where, Object... paras)//查询第一个
model.paginateAll(int pageNumber, int pageSize)//分页查询所以数据
model.paginateBy(int pageNumber, int pageSize, String where, Object... paras)//根据where条件分页
model.updateAll(String set, Object... paras)//更新所以数据 set格式不支持前缀直接列名1=value，列名2=value...
model.updateBy(String set, String where, Object... paras)//通过条件更新
model.deleteAll()//逻辑删除  需要表中存在一个  deleted_at Date类型  逻辑删除的时候存入时间  实际是update该值/数据安全
model.deleteBy(String where, Object... paras)//根据条件逻辑删除
model.dropAll()//物理删除 执行sql里的delete
model.dropBy(String where, Object... paras)//根据条件物理删除
model.countAll()//count函数 计算总数量
model.countBy(String where, Object... paras)//根据条件计算数量
//等等.... 还有getTableName(),getSelectSql(),getFromSql()....  大家看源码

```

JaxbKit 对象和xml之间的相互转换
参考[jfinal-sqlinxml](https://github.com/Dreampie/jfinal-sqlinxml)插件

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