package com.wpx.service.impl;

import com.wpx.dao.UserDao;
import com.wpx.domain.User;
import com.wpx.service.UserService;
import com.wpx.utils.BeanFactory;
import com.wpx.utils.MailUtils;

public class UserServiceImpl implements UserService{

	/**
	 * 用户注册
	 */
	@Override
	public void regist(User user)  throws Exception{
		UserDao dao=(UserDao) BeanFactory.getBean("UserDao");
		dao.add(user);
		//发送邮件
		
		//email:收件人地址
		//emailMsg:邮件的内容
		String emailMsg="欢迎您注册成我们的一员,<a href='http://localhost/store/user?method=active&code="+user.getCode()+"'>点此激活</a>";
		MailUtils.sendMail(user.getEmail(), emailMsg);
	}

	/**
	 * 用户激活
	 * @throws Exception 
	 */
	@Override
	public User active(String code) throws Exception {
		UserDao dao=(UserDao) BeanFactory.getBean("UserDao");
		//1.通过code获取一个用户
		User user=dao.getByCode(code);
		
		//2.判断用户是否为空
		if(user==null){
			return null;
		}
		
		//3.修改用户状态
		//将用户的状态设置为1
		user.setState(1);
		dao.update(user);
		
		return user;
	}

	/**
	 * 用户登录
	 */
	@Override
	public User login(String username, String password) throws Exception {
		UserDao dao=(UserDao) BeanFactory.getBean("UserDao");
		return dao.getByUsernameAndPwd(username,password);
	}

}
