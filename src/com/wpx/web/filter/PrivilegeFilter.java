package com.wpx.web.filter;

import com.wpx.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PrivilegeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        //1.强转
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //2.业务逻辑
        //从session中获取user 判断user是否为空 若为空 请求转发
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("msg", "您好，请先登录!");
            request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
            return;
        }

        //3.放行
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
