package com.wpx.web.servlet;

import com.wpx.constant.Constant;
import com.wpx.domain.User;
import com.wpx.myconventer.MyConventer;
import com.wpx.service.UserService;
import com.wpx.utils.BeanFactory;
import com.wpx.utils.MD5Utils;
import com.wpx.utils.UUIDUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 和用户相关的servlet
 */
public class UserServlet extends BaseServlet {

	/**
	 * 跳转到 注册页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/jsp/register.jsp";
	}
	
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.封装数据
		User user = new User();
		
		//注册自定义转化器
		ConvertUtils.register(new MyConventer(), Date.class);
		BeanUtils.populate(user, request.getParameterMap());
		
		//1.1 设置用户id
		user.setUid(UUIDUtils.getId());
		
		//1.2 设置激活码
		user.setCode(UUIDUtils.getCode());
		
		//1.3加密密码
		user.setPassword(MD5Utils.md5(user.getPassword()));
		
		//2.调用service完成注册
		UserService s=(UserService) BeanFactory.getBean("UserService");
		s.regist(user);
		
		//3.页面请求转发
		request.setAttribute("msg", "用户注册已成功,请去邮箱激活~~");
		
		return "/jsp/msg.jsp";
	}
	
	/**
	 * 用户激活
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取激活码
		String code = request.getParameter("code");
		
		//2.调用service完成激活
		UserService s=(UserService) BeanFactory.getBean("UserService");
		User user=s.active(code);
		
		if(user==null){
			//通过激活码没有找到用户
			request.setAttribute("msg", "请重新激活");
		}else{
			//添加信息
			request.setAttribute("msg", "激活成功");
		}
		//3.请求转发到msg.jsp
		
		return "/jsp/msg.jsp";
	}
	
	/**
	 * 跳转到登录页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/jsp/login.jsp";
	}
	
	/**
	 * 登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取用户名和密码
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		password=MD5Utils.md5(password);
		
		//2.调用serive完成登录操作 返回user
		UserService s=(UserService) BeanFactory.getBean("UserService");
		User user=s.login(username,password);
		
		//3.判断用户
		if(user==null){
			//用户名密码不匹配
			request.setAttribute("msg", "用户名密码不匹配");
			return "/jsp/login.jsp";
		}else{
			//继续判断用户的状态是否激活
			if(Constant.USER_IS_ACTIVE!=user.getState()){
				request.setAttribute("msg", "用户未激活");
				return "/jsp/login.jsp";
			}
		}
		
		//4.将user放入session中 重定向
		request.getSession().setAttribute("user", user);
		response.sendRedirect(request.getContextPath()+"/");//  /store
		
		return null;
	}

	
	public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//干掉session
		request.getSession().invalidate();
		
		//重定向
		response.sendRedirect(request.getContextPath());
		
		//处理自动登录
		
		return null;
	}
}
