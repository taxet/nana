package moe.sayuri.nana.rest.config;

import moe.sayuri.nana.rest.access.AccessLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@EnableWebMvc
@Configuration
public class RestConfig implements WebMvcConfigurer {
    @Autowired
    public AccessLog accessLog;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(accessLog);
        registration.addPathPatterns("/nana/api/**");
    }
}
