package com.wpx.web.servlet;

import com.wpx.domain.PageBean;
import com.wpx.domain.Product;
import com.wpx.service.ProductService;
import com.wpx.utils.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 商品servlet
 */
public class ProductServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 通过id查询单个商品详情
     *
     * @throws Exception
     */
    public String getById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1.获取商品的id
        String pid = request.getParameter("pid");

        //2.调用service
        //ProductService ps=new ProductServiceImpl();
        ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
        Product p = ps.getByPid(pid);

        //3.将结果放入request中 请求转发
        request.setAttribute("bean", p);
        return "/jsp/product_info.jsp";
    }

    /**
     * 分页查询数据
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findByPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1.获取类别 当前页  设置一个pagesize
        String cid = request.getParameter("cid");
        int currPage = Integer.parseInt(request.getParameter("currPage"));
        int pageSize = 12;


        //2.调用service 返回值pagebean
        ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
        PageBean<Product> bean = ps.findByPage(currPage, pageSize, cid);

        //3.将结果放入request中 请求转发
        request.setAttribute("pb", bean);
        return "/jsp/product_list.jsp";
    }

}
