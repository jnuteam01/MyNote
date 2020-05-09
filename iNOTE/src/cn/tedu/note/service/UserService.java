package cn.tedu.note.service;

import java.io.Serializable;

import cn.tedu.note.entity.User;

public interface UserService extends Serializable{
	/*
	 * 登录方法
	 * 登录成功返回用户的信息，
	 * 失败抛出异常：NameOrPasswordException  用户名或密码为空
	 */	
	/**
	 * 
	 * @param name
	 * @param password
	 * @return 注册个成功以后的角色
	 * @throws NameOrPasswordException
	 */
	User login(String name,String password) throws NameOrPasswordException;
	User regist(String name,String password,String nick) throws UserExistException;
}
