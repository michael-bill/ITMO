package org.michael.laba4_back.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.michael.laba4_back.model.AuthToken;
import org.michael.laba4_back.repository.AuthTokenRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

import static org.michael.laba4_back.Utils.getMessage;

public class AuthFilter implements Filter {

    private AuthTokenRepository authTokenRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
        this.authTokenRepository = ctx.getBean(AuthTokenRepository.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String tokenHeader = request.getHeader("auth-token");
        AuthToken token = authTokenRepository.findByToken(tokenHeader);
        response.setContentType("application/json");
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(getMessage("Wrong auth token"));
            return;
        }
        if (token.isExpired()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(getMessage("Auth token expired"));
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
