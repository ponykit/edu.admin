package com.edu.admin.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Slf4j
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private String[] authenticationRequiresURL;

    private String[] authenticationExcludesURL;

    @Autowired
    public WebConfig(String[] getAuthenticationRequiresURL
            , String[] getAuthenticationIgnoresURL) {
        this.authenticationRequiresURL = getAuthenticationRequiresURL;
        this.authenticationExcludesURL = getAuthenticationIgnoresURL;

        log.info("Authentication requires url for Spring Security Principal injecting intercepter : {}", (Object[]) authenticationRequiresURL);
        log.info("Authentication excludes url for Spring Security Principal injecting intercepter : {}", (Object[]) authenticationExcludesURL);
    }


    /**
     * static 디렉토리의 리소스 매핑을 위한 선언
     * Spring Boot에 의해 자동 설정되나 @EnableWebMvc 어노테이션이 추가되면 해당 부분이 자동으로 설정되지 않음.
     * 설정되지 않았을 때는 static/** 경로상의 리소스를 접근하려 할 때 "No mapping found for HTTP request with URI" 메시지가
     * 출력되면서 DispatchServlet 관련 메시지가 출력된다.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/static/fonts/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
    }

}
