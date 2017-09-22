package top.kmacro.blog.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Zhangkh on 2017-09-06.
 */
@WebFilter(filterName = "webContextFilter", urlPatterns = "/*")
public class WebContextFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return;
        }

        WebContext.init(request, response);
        try {
            filterChain.doFilter(request, response);
        } finally {
            WebContext.destroy();
        }
    }

    @Override
    public void destroy() {
    }
}
