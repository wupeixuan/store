package com.wpx.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.wpx.dao.UserDao;
import com.wpx.domain.User;
import com.wpx.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao{

	/**
	 * 用户注册
	 * @throws SQLException 
	 */
	@Override
	public void add(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		/**
		 *  `uid` VARCHAR(32) NOT NULL,
		  `username` VARCHAR(20) DEFAULT NULL,
		  `password` VARCHAR(100) DEFAULT NULL,
		  
		  `name` VARCHAR(20) DEFAULT NULL,
		  `email` VARCHAR(30) DEFAULT NULL,
		  `telephone` VARCHAR(20) DEFAULT NULL,
		  
		  `birthday` DATE DEFAULT NULL,
		  `sex` VARCHAR(10) DEFAULT NULL,
		  `state` INT(11) DEFAULT NULL,
		  `code` VARCHAR(64) DEFAULT NULL,
		 */
		String sql="insert into user values(?,?,?,?,?,?,?,?,?,?);";
		qr.update(sql, user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),
				user.getBirthday(),user.getSex(),user.getState(),user.getCode());
		
		
	}

	/**
	 * 通过激活码获取一个用户
	 */
	@Override
	public User getByCode(String code) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from user where code = ? limit 1";
		return 	qr.query(sql, new BeanHandler<>(User.class), code);
	}

	/**
	 * 修改用户
	 */
	@Override
	public void update(User user) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update user set username = ?,password = ? ,name=?,email=?,birthday = ?,state = ?,code=? where uid =? ";
		qr.update(sql, user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getBirthday(),
				user.getState(),null,user.getUid());
	}

	/**
	 * 用户登录
	 */
	@Override
	public User getByUsernameAndPwd(String username, String password) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from user where username = ? and password = ? limit 1";
		return qr.query(sql, new BeanHandler<>(User.class), username,password);
	}

}
