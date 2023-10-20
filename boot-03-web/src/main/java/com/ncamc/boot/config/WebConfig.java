package com.ncamc.boot.config;

import com.ncamc.boot.bean.Pet;
import com.ncamc.boot.converter.NcamcMessageConverter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.accept.ParameterContentNegotiationStrategy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            /**
             * 重写configureContentNegotiation
             * @param configurer
             */
            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                //Map<String, MediaType> mediaTypes
                Map<String, MediaType> mediaTypes = new HashMap<>();
                //指定支持解析哪些参数对应的哪些媒体类型
                mediaTypes.put("json",MediaType.APPLICATION_JSON);
                mediaTypes.put("xml",MediaType.APPLICATION_XML);
                mediaTypes.put("ncamc",MediaType.parseMediaType("application/x-ncamc"));
                ParameterContentNegotiationStrategy parameterContentNegotiationStrategy = new ParameterContentNegotiationStrategy(mediaTypes);
                HeaderContentNegotiationStrategy headerContentNegotiationStrategy = new HeaderContentNegotiationStrategy();
                //基于参数的内容协商和基于请求头的内容协商都需要
                configurer.strategies(Arrays.asList(parameterContentNegotiationStrategy,headerContentNegotiationStrategy));
                WebMvcConfigurer.super.configureContentNegotiation(configurer);
            }

            /**
             * SpringMVC的任何东西都可以在webMvcConfigurer里修改:
             * 接口里与MessageConverter有关的接口有两个
             * 1. configureMessageConverters
             * 2. extendMessageConverters
             * 用下面的
             * @param converters
             */
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new NcamcMessageConverter());
            }

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
