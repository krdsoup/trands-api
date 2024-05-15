package kr.co.trands.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 서비스 접근을 위한 bean 사용
        registry.addInterceptor(tokenInterceptor())
		        .excludePathPatterns("/swagger-ui/*"
		        		, "/v3/api-docs/*"
		        		, "/graphql");
    }
    
    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }

}