package utn.tacs.grupo5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import utn.tacs.grupo5.config.interceptor.RequestLoggingInterceptor;
import utn.tacs.grupo5.security.ClearContextInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(new RequestLoggingInterceptor());
        registry.addInterceptor(new ClearContextInterceptor());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
