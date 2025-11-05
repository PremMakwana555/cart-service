package com.ecommerce.cart_service.config;

import com.ecommerce.cart_service.security.HeaderAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Register the header authentication filter as a servlet filter. This avoids
 * depending on a specific Spring Security HttpSecurity API and works even if
 * the project doesn't use spring-boot-starter-security.
 * WARNING: This filter trusts the X-User-Id header and should only be used
 * if the header is injected by a trusted gateway.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public FilterRegistrationBean<HeaderAuthenticationFilter> headerAuthFilterRegistration() {
        FilterRegistrationBean<HeaderAuthenticationFilter> reg = new FilterRegistrationBean<HeaderAuthenticationFilter>(new HeaderAuthenticationFilter("X-User-Id"));
        reg.addUrlPatterns("/*");
        reg.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return reg;
    }
}
