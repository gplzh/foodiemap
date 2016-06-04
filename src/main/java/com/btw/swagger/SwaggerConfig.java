package com.btw.swagger;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 本类是配置Swagger UI测试API功能
 */

@Configuration
@EnableSwagger
public class SwaggerConfig {
    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo()).includePatterns("/api/.*", "/user/.*", "/geo/.*")
                .apiVersion("1.0.0");
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "FoodieMap API",
                "需要使用HTTP Basic登录，或提供x-auth-token<br>"
                        + "包含参数Parameters Data Type=Pageable时，有三个通用参数可以使用,比如：size=20&page=0&sort=popular,desc，"
                        + "默认：size=20表明每页20行，page=0第几页，sort=popular,desc(asc默认正序，desc倒序)",
                null, null, null, null);
        return apiInfo;
    }
}