package com.wpx.web.servlet;

import com.wpx.domain.Product;
import com.wpx.service.ProductService;
import com.wpx.utils.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 后台商品管理
 */
public class AdminProductServlet extends BaseServlet {
    /**
     * 查询所有商品
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1.调用service 查询所有 返回一个list
        ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
        List<Product> list = ps.findAll();
        //2.将list放入request域中 请求转发
        request.setAttribute("list", list);
        return "/admin/product/list.jsp";
    }

    public void addUI(HttpServletRequest request, HttpServletResponse response) throws Exception {

    }
}
