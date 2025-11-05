package com.ecommerce.cart_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Principal;

/**
 * Populate the request Principal from a trusted header set by the API Gateway.
 * WARNING: Only use this when the header is set by a trusted gateway on a secure internal network
 * (for example behind mTLS or an internal load balancer). Do NOT accept user-supplied headers from the public internet.
 */
public class HeaderAuthenticationFilter extends OncePerRequestFilter {
    private final String headerName;

    public HeaderAuthenticationFilter(String headerName) {
        this.headerName = headerName;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String userId = request.getHeader(headerName);

        if (userId != null) {
            Principal principal = () -> userId;
            HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(request) {
                @Override
                public Principal getUserPrincipal() {
                    return principal;
                }

                @Override
                public String getRemoteUser() {
                    return userId;
                }
            };
            chain.doFilter(wrapper, response);
            return;
        }

        chain.doFilter(request, response);
    }
}
