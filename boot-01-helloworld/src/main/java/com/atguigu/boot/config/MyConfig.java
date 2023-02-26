package com.atguigu.boot.config;

import ch.qos.logback.core.db.DBHelper;
import com.atguigu.boot.bean.Car;
import com.atguigu.boot.bean.Pet;
import com.atguigu.boot.bean.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

/**
 * 1. 配置类里面使用@Bean标注方法上给容器注册组件,默认也是单实例的
 * 2. 配置类本身也是组件
 * 3. proxyBeanMethods: 是否代理bean的方法,这个作用很大
 * Full(proxyBeanMethods = true) 配置类里给容器注册组件的方法在外部调用时,每次都会去容器里找组件,保证每个@Bean方法被调用时返回的组件都是单实例的
 *
 * Lite(proxyBeanMethods = false)容器里不会保留代理对象,每次调用都会产生新的组件,即每个@Bean方法被调用多少次返回的组件都是新创建的
 * 组件依赖必须使用Full模式默认。其他默认是否Lite模式
 * 这个设置解决的是组件依赖问题:
 * 比如user要养一个pet,给容器中注册组件用户(user,user组件依赖了Pet组件),user想要在容器中找到之前注册的pet,如果用false模式,则每次新建一个pet
 *  ○ 最佳实战
 *     ■ 配置 类组件之间无依赖关系用Lite模式加速容器启动过程，减少判断
 *     ■ 配置类组件之间有依赖关系，方法会被调用得到之前单实例组件，用Full模式
 * 4. 还可以通过Import给容器导入组件
 * @Import({User.class, DBHelper.class})
 * 给容器中自动创建出这两个组件,默认组件的名字就是全类名
 */
//@ConditionalOnBean(name = "tom")
//@ConditionalOnMissingBean(name = "tom")
@Import({User.class, DBHelper.class})
@Configuration(proxyBeanMethods = true)  //告诉springboot这是一个配置类 等同于配置文件
@ImportResource("classpath:beans.xml")  //允许spring导入以前写的配置类
@EnableConfigurationProperties(Car.class) //开启属性配置功能
//@EnableConfigurationProperties这个注解有两个功能
// 1. 开启Car的配置绑定功能(@ConfigurationProperties("mycar"))
// 2. 把Car这个组件自动注册到容器中
// 推荐使用这种方式,因为如果引用第三方包的话,包上可能没有@Component注解,所以只能用@EnableConfigurationProperties+@ConfigurationProperties("mycar")
public class MyConfig {
    /**
     * 外部无论对配置类中的这个组件注册方法调用多少次获取的都是之前注册容器中的单实例对象
     * @return
     */

    @Bean("tom")
    public Pet tomcatPet(){
        return new Pet("tomcat");
    }

    //@ConditionalOnBean(name = "tom")//当容器中有名字为tom的组件时才会给容器中注册user01,这个也可以设置到类名上,表示全部类都会检查容器中是否有名为tom的组件
    //@ConditionalOnMissingBean(name = "tom")
    @Bean //给容器中添加组件,以方法名作为组件的id.返回类型就是组件类型,返回的值就是组件在容器中的实例
    public User user01(){
        //user组件依赖pet组件
        //如果proxyBeanMethods是true,则user下的pet就是容器里的pet
        User zhangsan = new User("zhangsan", 18);
        zhangsan.setPet(tomcatPet());
        return zhangsan;
    }

}

