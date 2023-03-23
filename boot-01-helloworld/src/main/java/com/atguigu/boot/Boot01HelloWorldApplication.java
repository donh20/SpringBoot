package com.atguigu.boot;

import ch.qos.logback.core.db.DBHelper;
import com.atguigu.boot.bean.Car;
import com.atguigu.boot.bean.Pet;
import com.atguigu.boot.bean.User;
import com.atguigu.boot.config.MyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 典型的web开发，
 *  - 原生spring，创建一个项目，导入spring，springmvc，写配置，编写整合配置，开发代码，最终把应用部署到tomcat上
 *  - springboot做法：参考官方文档Developing Your First Spring Boot Application
 *  (https://docs.spring.io/spring-boot/docs/3.0.2/reference/html/getting-started.html#getting-started.first-application)
 *  1. 写pom文件
 *  <parent>
 *     <groupId>org.springframework.boot</groupId>
 *     <artifactId>spring-boot-starter-parent</artifactId>
 *     <version>3.0.2</version>
 *  </parent>
 *  2. 增加类路径依赖也就是增加场景启动器starter（记得enable auto import）
 *  <dependencies>
 *     <dependency>
 *         <groupId>org.springframework.boot</groupId>
 *         <artifactId>spring-boot-starter-web</artifactId>
 *     </dependency>
 *  </dependencies>
 *  3. 编写代码
 *  import org.springframework.boot.SpringApplication;
 *  import org.springframework.boot.autoconfigure.SpringBootApplication;
 *  import org.springframework.web.bind.annotation.RequestMapping;
 *  import org.springframework.web.bind.annotation.RestController;
 *
 * @RestController
 * @SpringBootApplication
 * public class MyApplication {
 *
 *     @RequestMapping("/")
 *     String home() {
 *         return "Hello World!";
 *     }
 *
 *     public static void main(String[] args) {
 *         SpringApplication.run(MyApplication.class, args);
 *     }
 *
 * }
 *
 * 编写业务逻辑
 * 至此可以完美执行
 *
 *
 *
 *  4. 运行
 *  直接运行main方法即可
 *
 *
 *  5. 打成Jar包直接运行，在pom文件里添加spring-boot-maven-plugin
 *  <build>
 *     <plugins>
 *         <plugin>
 *             <groupId>org.springframework.boot</groupId>
 *             <artifactId>spring-boot-maven-plugin</artifactId>
 *         </plugin>
 *     </plugins>
 *  </build>
 */

/**
 * 主程序类一般叫MainApplication,也叫主配置类，类名可以随便取，但是一定要标注一个注解：
 * @SpringBootApplication: 告诉SpringBoot这是一个SpringBoot应用
 */


/*
* 自动配置原理入门
* 一,引导加载自动配置类
* SpringBootApplication:
* 1. @SpringBootConfiguration
* 2. @EnableAutoConfiguration
* 3. @ComponentScan("com.atguigu.boot")
*
* EnableAutoConfiguration:
* 1. @AutoConfigurationPackage
* 2. @Import(AutoConfigurationImportSelector.class)

* 打开AutoConfigurationPackage,发现是@Import(AutoConfigurationPackages.Registrar.class)
* 给容器中导入一个Registrar,即利用Registrar给容器中导入一系列组件
* public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
*   register(registry, new PackageImports(metadata).getPackageNames().toArray(new String[0]));
* }
* 传入注解的metadata原信息,即本注解标注在哪,都有什么属性值:注解的源信息标注在MainApplication上
* 即,将指定的一个包下的所有组件导入进来,哪个包呢?是MainApplication所在的包下
* 获取注解元信息的包名 -> 转为String数组 -> 通过register这个方法，扫描这个包下面的所有符合条件的组件，注册进IOC容器中
*
* 然后打开Import(AutoConfigurationImportSelector.class)源码分析
* 1. 利用getAutoConfigurationEntry(annotationMetadata);给容器中批量导入一些组件
* 2. 调用`List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes)`获取到所有需要导入到容器中的配置类
* 3. 利用工厂加载 `Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader);`得到所有的组件
* 4. 读源码发现,实际上是从从`META-INF/spring.factories`这个位置来加载一个文件。
* - 默认扫描我们当前系统里面所有`META-INF/spring.factories`位置的文件
* - `spring-boot-autoconfigure-2.3.4.RELEASE.jar`包里面也有`META-INF/spring.factories`
* 从22到148,一共有148-22+1=127行,即127个组件
* 配置文件写死了,spring-boot一启动,就会加载所有的配置类,注意是所有的配置类
*
*
* 二,按需开启自动配置项
* 但是会全部加载吗?不会,为什么?
* 得益于按需加载的注解(比如当有某个class存在时,某配置才会启用,比如当批处理类被引入时,批处理配置才会被启用)
* 虽然我们127个场景的所有自动配置启动的时候默认全部加载。 xxxxAutoConfiguration按照条件装配规则 （Conditional) ,最终会按需配置。
* 比如如`AopAutoConfiguration`类:
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "spring.aop",name = "auto",havingValue = "true",matchIfMissing = true)
public class AopAutoConfiguration {
    public AopAutoConfiguration() {
    }
	...
}
* 三,修改默认配置
@Bean
@ConditionalOnBean(MultipartResolver.class)  //容器中有这个类型组件
@ConditionalOnMissingBean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME) //容器中没有这个名字 multipartResolver 的组件
public MultipartResolver multipartResolver(MultipartResolver resolver) {
    //给@Bean标注的方法传入了对象参数，这个参数的值就会从容器中找。
    //SpringMVC multipartResolver。防止有些用户配置的文件上传解析器不符合规范
    // Detect if the user has created a MultipartResolver but named it incorrectly
    return resolver;
}
给容器中加入了文件上传解析器；

* SpringBoot默认会在底层配好所有的组件。但是如果用户自己配置了以用户的优先
@Bean
@ConditionalOnMissingBean
public CharacterEncodingFilter characterEncodingFilter() {
}
总结：
● SpringBoot先加载所有的自动配置类  xxxxxAutoConfiguration
● 每个自动配置类按照条件进行生效，默认都会绑定配置文件指定的值。(去xxxxProperties里面拿。xxxProperties和配置文件进行绑定)
● 生效的配置类就会给容器中装配很多组件
● 只要容器中有这些组件，相当于这些功能就有了
● 想要定制化配置也很简单
  ○ 用户直接自己写组件,加@Bean,即可替换底层的组件
  ○ 用户去看这个组件(比如@EnableConfigurationProperties(WebMvcProperties.class)里的WebMvcProperties.class)是获取的配置文件什么值就去修改(spring.mvc.xxx)就行了。
xxxxxAutoConfiguration ---> 组件  ---> 这些组件去xxxxProperties里面拿值  ----> xxxxProperties又从application.properties取值
*
* 不同Properties的含义参考官方文档,也可以通过源码直接点开EnableConfigurationProperties(xxxxProperties.class)看
* https://docs.spring.io/spring-boot/docs/3.0.2/reference/html/application-properties.html#appendix.application-properties
*
*
*
* 四,最佳实践
● 引入场景依赖
  ○ https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-starter
● 查看自动配置了哪些（选做）
  ○ 自己分析，引入场景对应的自动配置一般都生效了
  ○ 配置文件中debug=true开启自动配置报告。Negative（不生效）\Positive（生效）
● 是否需要修改
  ○ 参照文档修改配置项
    ■ https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html#common-application-properties
    ■ 自己分析。xxxxProperties绑定了配置文件的哪些。
    * 先考虑修改官方组件的配置
  ○ 自定义加入或者替换组件
    ■ @Bean、@Component。。。
    * 再考虑自己实现组件
  ○ 自定义器  XXXXXCustomizer对已有的组件进行自定义
  ○ 不断引入SpringBoot的高阶特性
* */
//@SpringBootApplication
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan("com.atguigu.boot")
public class Boot01HelloWorldApplication {
    public static void main(String[] args) {
//        1. 返回IOC容器
        ConfigurableApplicationContext run = SpringApplication.run(Boot01HelloWorldApplication.class, args);
//        2. 查看容器里面的组件
//        String[] names = run.getBeanDefinitionNames();
//        for (String name : names) {
//
//            System.out.println(name);
//        }
//        3. 从容器中获取组件
//        Pet tom1 = run.getBean("tom", Pet.class);
//        Pet tom2 = run.getBean("tom", Pet.class);
//        System.out.println("组件: "+ (tom1==tom2));
//
//        4. com.atguigu.boot.config.MyConfig$$EnhancerBySpringCGLIB
//        MyConfig bean = run.getBean(MyConfig.class);
//
//        System.out.println("MyConfig: "+bean);
//        // 如果@Configuration(proxyBeanMethods = true),则获取到的就是代理对象
//        // 代理对象调用方法时,springboot总会检查这个组件是否在容器中.如果有,就不会再实例化一次.
//        // 即,保持组件单实例
//        User user01 = bean.user01();
//        User user02 = bean.user01();
//        System.out.println(user02 == user01);
//
//        User user = run.getBean("user01", User.class);
//        Pet tom01 = run.getBean("tom",Pet.class);
//        System.out.println("tom是否是user用户的宠物: "+ (user.getPet() == tom01));

        //5. 从容器中获取user组件(@Import({User.class, DBHelper.class}))但是User组件已经有很多个了,那怎么办?找User.class类型的组件即可
        String[] beanNamesForUser = run.getBeanNamesForType(User.class);
        System.out.println("==============");
        for (String s : beanNamesForUser) {
            System.out.println(s);
        }
//        String[] beanNamesForDBHelper = run.getBeanNamesForType(DBHelper.class);
//        for (String s : beanNamesForDBHelper) {
//            System.out.println(s);
//        }
        //6.
        System.out.println("==============");
        boolean tom = run.containsBean("tom");
        System.out.println("容器中是否有tom组件: " + tom);

        boolean user01 = run.containsBean("user01");
        System.out.println("容器中是否有user01组件: "+ user01);

        boolean tom22 = run.containsBean("tom22");
        System.out.println("容器中是否有tom22组件: "+ tom22);

        boolean haha = run.containsBean("haha");
        System.out.println("容器中是否有haha组件: "+ haha);

        boolean hehe = run.containsBean("hehe");
        System.out.println("容器中是否有hehe组件: "+ hehe);
//
//        String[] beanNamesForType = run.getBeanNamesForType(Car.class);
//        for (String s : beanNamesForType) {
//            System.out.println(s);
//            boolean car = run.containsBean(s);
//            System.out.println("容器中是否有mycar组件: " + car);
//        }
    }
}
