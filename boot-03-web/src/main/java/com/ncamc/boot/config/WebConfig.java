package com.ncamc.boot.config;

import com.ncamc.boot.bean.Pet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

@Configuration(proxyBeanMethods = false) //容器里放组件没有依赖，所以用false可以快速放
public class WebConfig /*implements WebMvcConfigurer*/ {

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter(){

        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        hiddenHttpMethodFilter.setMethodParam("_m");
        return hiddenHttpMethodFilter;
    }





//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        UrlPathHelper urlPathHelper = new UrlPathHelper();
//        //不移除分号后面的内容,矩阵变量功能就可以正常生效
//        urlPathHelper.setRemoveSemicolonContent(false);
//        configurer.setUrlPathHelper(urlPathHelper);
//    }


    @Bean
    public WebMvcConfigurer webMvcConfigurer(){

        return new WebMvcConfigurer() {
            @Override
            public void configurePathMatch(PathMatchConfigurer configurer) {
                UrlPathHelper urlPathHelper = new UrlPathHelper();
                //不移除分号后面的内容,矩阵变量功能就可以正常生效
                urlPathHelper.setRemoveSemicolonContent(false);
                configurer.setUrlPathHelper(urlPathHelper);
                //WebMvcConfigurer.super.configurePathMatch(configurer);
            }

            /**
             * 1、WebMvcConfigurer定制化SpringMVC的功能
             * 接口类从Java8开始有默认的实现,因此只要重写我们关注的方法就可以了
             *
             * 观察WebMvcConfigurer里有addFormatters接口，尝试重写
             * @param registry
             */
            @Override
            public void addFormatters(FormatterRegistry registry) {
                //观察Converter接口里有addConverter(Converter<?, ?> converter);
                //实现source到target类型的转换即可
                registry.addConverter(new Converter<String, Pet>() {
                    @Override
                    public Pet convert(String source) {

                        if (!StringUtils.isEmpty(source)) {

                            String[] split = source.split(",");
                            Pet pet = new Pet(split[0],Integer.parseInt(split[1]));
                            return pet;
                        } else {
                            return null;
                        }

                    }
                });
            }
        };
    }

}
