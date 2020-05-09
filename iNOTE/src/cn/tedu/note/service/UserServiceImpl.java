package cn.tedu.note.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.User;
import cn.tedu.note.util.Md5;

@Service("userService")
public class UserServiceImpl implements UserService{

	
	private static final long serialVersionUID = 1L;
	@Autowired   //@Resource
	private UserDao userDao;

	

	/**
	 * 该方法检验账号密码
	 * 检验密码：不用明文，而用摘要。摘要是和数据一对一的。
	 * 摘要的用途：比较数据的完整性
	 * 
	 * 摘要的特点：
	 * 1.数据一样，摘要一定一样
	 * 2.数据不同，摘要一定不同
	 * 3.任何数据的摘要结果长度是一样的
	 * 4.摘要是单向算法：数据摘要无法还原为数据
	 */
	public User login(String name, String password) throws NameOrPasswordException {
		//入口参数检查
		if(name==null||name.trim().isEmpty()){
			throw new NameOrPasswordException("用户名不能为空");
			
		}
		if(password==null||password.trim().isEmpty()){
			throw new NameOrPasswordException("密码不能为空");
			
		}
		//从业务层查询用户信息
		User user=userDao.findUserByName(name);
		if(user==null){
			throw new NameOrPasswordException("用户名或者密码错误");
		}
		/* 
		 * 因为现在数据库password字段都是未加盐的数据，而新注册的password都加盐了，
		 * 因此需要用||实现兼容现有数据库数据和要添加数据库数据
		 * Md5.md5(password)是将页面传来的数据转换为对应的摘要
		 * Md5.saltMd5(password)是将页面传来的数据转换为对应的摘要而且加了盐，
		 * （就是将用户输入的密码再加内容变复杂）
		 * 数据库存储用户密码时，存储其密码的摘要
		 * 用户注册时也要使用摘要。
		 */
		if(user.getPassword().equals(                 
				Md5.md5(password))||
				user.getPassword().equals(
						Md5.saltMd5(password))){
			return user;//登录成功
		}
		throw new NameOrPasswordException("用户名或者密码错误");

	}
	public User regist(String name,String password,String nick)
			throws UserExistException{
		String rule = "^\\w{3,10}$";
		if(name==null||name.trim().isEmpty()){
			throw new ServiceException("用户名不能为空");
		}
		if(!name.matches(rule)){
			throw new ServiceException("用户名不合格");
		}
		if(password==null||name.trim().isEmpty()){
			throw new ServiceException("密码不能为空");
		}
		if(nick==null||nick.trim().isEmpty()){
			throw new ServiceException("昵称不能为空！");
		}
		rule = "^.{3,10}$";
		if(!nick.matches(rule)){
			throw new ServiceException("昵称不合格！");
		}
		User user = userDao.findUserByName(name);
		if(user!=null){
			throw new UserExistException("用户已存在！");
		}
		String id = UUID.randomUUID().toString();
		String pwd = Md5.saltMd5(password);
		user = new User(id,name,pwd,"",nick);
		userDao.saveUser(user);
		return user;
	}
}
