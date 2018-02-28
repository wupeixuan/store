package com.wpx.web.servlet;

import com.wpx.domain.Category;
import com.wpx.service.CategoryService;
import com.wpx.utils.BeanFactory;
import com.wpx.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {

	/**
	 * 查询所有的分类
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.调用categoryservice 查询所有的分类 返回值list
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> clist = null;
		try {
			clist = cs.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 2.将返回值转成json格式 返回到页面上
		//request.setAttribute("clist", clist);
		String json = JsonUtil.list2json(clist);
		
		//3.写回去
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(json);
		
		return null;
	}

}
