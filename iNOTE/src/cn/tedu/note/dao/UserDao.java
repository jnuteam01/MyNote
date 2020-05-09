package cn.tedu.note.dao;

import cn.tedu.note.entity.User;

public interface UserDao {
	public void saveUser(User user);
	public User findById(String id);
	public User findUserByName(String name);
}
