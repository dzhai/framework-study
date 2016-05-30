## sitemesh3
## Sitemesh 3 简介
Sitemesh 是一个网页布局和修饰的框架，基于 Servlet 中的 Filter
![]()
## 使用及配置说明
项目demo基于sitemesh-3.0.1
### 在pom添加依赖 

```
<dependency>
	<groupId>org.sitemesh</groupId>
	<artifactId>sitemesh</artifactId>
	<version>3.0.1</version>
</dependency>
```

### web.xml 添加拦截器

```
<filter>
	<filter-name>sitemesh</filter-name>
	<filter-class>org.sitemesh.config.ConfigurableSiteMeshFilter</filter-class>
</filter>
<filter-mapping>
	<filter-name>sitemesh</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>
	
```

### 创建 sitemesh3.xm (默认在/WEB-INFO/sitemesh3.xml 必须是这个名字 Hard Code)
sitemesh3提供2种配置方式 **java和xml**，具体请看[Configuring SiteMesh 3](http://wiki.sitemesh.org/wiki/display/sitemesh3/Configuring+SiteMesh+3)

```
<?xml version="1.0" encoding="UTF-8"?>
<sitemesh>

	<!--默认情况下， sitemesh 只对 HTTP 响应头中 Content-Type 为 text/html 的类型进行拦截和装饰， 我们可以添加更多的 mime 类型 -->
	<mime-type>text/html</mime-type>
	<mime-type>application/vnd.wap.xhtml+xml</mime-type>
	<mime-type>application/xhtml+xml</mime-type>

	 <!-- 默认装饰器，当下面的路径都不匹配时，启用该装饰器进行装饰 -->
	 <mapping decorator="/defaulmain.jsp"/>
	  
	<!-- 指明满足"*.jsp"的页面，将被"main.jsp"所装饰 -->
	<mapping path="*.jsp" decorator="/views/sitemesh3/main.jsp" />

	<!-- 指明满足"/exclude.jsp*"的页面，将被排除，不被装饰 -->
	<mapping path="/exclude.jsp" exclue="true" />

	<!-- 自定义标签 -->
	<content-processor>
		<tag-rule-bundle class="com.github.dzhai.sitemesh.tag.HeaderTagRuleBundle" />
		<tag-rule-bundle class="com.github.dzhai.sitemesh.tag.FooterTagRuleBundle" />
	</content-processor>
</sitemesh>
```

### 创建模板文件 main.jsp

```
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title><sitemesh:write property='title' /> -sitemesh</title>
<sitemesh:write property='head' />
</head>
<body>
	<header>header - <sitemesh:write property='header' /></header>
	<sitemesh:write property='body' />
	<footer>footer - <sitemesh:write property='footer' /></footer>
</body>
</html>

```

### 创建 具体页面index.jsp
```
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<title>index页面标题</title>
<script type="text/javascript">
	alert('script');
</script>
</head>
<body>
	<header>my header</header>
	<h2>Hello World!</h2>
	<footer>my footer</footer>
</body>
</html>
```

### 自定义tag

**FooterTagRuleBundle**
```
public class FooterTagRuleBundle implements TagRuleBundle {
	@Override
	public void install(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
		defaultState.addRule("footer",
				new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("footer"), false));

	}

	@Override
	public void cleanUp(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
	}
}
```

**HeaderTagRuleBundle**
```
public class HeaderTagRuleBundle implements TagRuleBundle {
	@Override
	public void install(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
		defaultState.addRule("header",
				new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("header"), false));

	}

	@Override
	public void cleanUp(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
	}
}
```

### 运行项目 访问 index.jsp

![此处输入图片的描述]()
**生成html源码**
```
<!DOCTYPE html>
<html>
<head>
<title>index页面标题 -sitemesh</title>


<script type="text/javascript">
	alert('script');
</script>

</head>
<body>
	<header>header - my header</header>
	
	
	<h2>Hello World!</h2>
	

	<footer>footer - my footer</footer>
</body>
</html>
```

## Link
1. [Sitemesh官网地址](http://wiki.sitemesh.org/wiki/display/sitemesh3/SiteMesh+3+Overview)
2. [Sitemesh 3 的使用及配置](http://www.cnblogs.com/luotaoyeah/p/3776879.html)
3. [Sitemesh VS Apache Tiles](http://blog.csdn.net/tbwood/article/details/40983143)
