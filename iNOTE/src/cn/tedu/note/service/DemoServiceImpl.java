package cn.tedu.note.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.User;
import cn.tedu.note.util.Md5;

@Service("demoService")
public class DemoServiceImpl implements DemoService {

	@Resource
	private UserDao userDao;
	
	/* (non-Javadoc)
	 * @see cn.tedu.note.service.DemoService#batchRegist(java.lang.String)
	 */
	@Transactional
	public List<String> batchRegist(
			String... userName){
		List<String> list = new ArrayList<String>();
		for(String name:userName){
			User user=userDao.findUserByName(name);
			if(user!=null){
				throw new ServiceException(
						"用户已经注册");
			}
			String uuid = UUID.randomUUID().toString();
			String pwd = uuid.substring(
					uuid.length()-6, uuid.length());
			list.add(pwd);
			String md5=Md5.saltMd5(pwd);
			user=new User(uuid, name, md5, "", name);
			userDao.saveUser(user); 
		}
		//Spring的事务只绑定到当前线程
		//业务层开启的子线程中的数据操作不参与
		//当前的事务！！！
		//需要单独处理子线程的事务
		new Thread(){
			public void run() {
				//这个操作不参与业务层事务
				//userDao.saveUser(...);
				//只线程中调用有 事务管理的方法即可
				someOpt();
			};
		}.start();
		
		return list;
	}
	
	@Transactional
	public void someOpt(){
		
	}
	
	/* (non-Javadoc)
	 * @see cn.tedu.note.service.DemoService#regist(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	public User regist(String name,
		String password, String nick){
		if(name==null || name.trim().isEmpty()){
			throw new ServiceException("名字不能为空");
		}
		if(password==null||password.trim().isEmpty()){
			throw new ServiceException("密码不能为空");
		}
		if(nick==null||nick.trim().isEmpty()){
			nick = name;
		}
		User user=userDao.findUserByName(name);
		if(user!=null){
			throw new ServiceException(name+"已经注册！");
		}
		String id=UUID.randomUUID().toString();
		String pwd = Md5.saltMd5(password);
		user = new User(id, name, pwd, "", nick);
		userDao.saveUser(user);
		user = userDao.findById(id);
		if(user==null){
			throw new ServiceException("注册失败！");
		}
		return user;
	}
	/* (non-Javadoc)
	 * @see cn.tedu.note.service.DemoService#batRegist(java.lang.String)
	 */
	@Transactional
	public List<String> batRegist(
			String... userName){
		List<String> list=
			new ArrayList<String>();
		for (String name : userName) {
			String pwd=randomPassword(6);
			regist(name,pwd, name);
			list.add(pwd);
		}
		//更多的 业务方法 ... update delete...
		return list;
	}

	private String randomPassword(int n) {
		char[] pwd=new char[n];
		String str = "123456789abcdefghijk";
		for(int i=0; i<pwd.length; i++){
			int index=(int)(Math.random()*str.length());
			pwd[i]=str.charAt(index);
		}
		return new String(pwd);
	}
}











