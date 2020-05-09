package test;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.note.dao.NoteDao;
import cn.tedu.note.dao.NotebookDao;
import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.Note;
import cn.tedu.note.entity.User;
import cn.tedu.note.service.NotebookService;
import cn.tedu.note.service.UserService;
import cn.tedu.note.util.Md5;

public class TestCase {
	private ApplicationContext ac;
	private UserDao dao;
	private NotebookDao notebookDao;
	private NoteDao noteDao;
	@Before
	public void init(){
		ac = new ClassPathXmlApplicationContext(
				"spring-mybatis.xml",
				"spring-service.xml");
		dao = ac.getBean("userDao",UserDao.class);
		notebookDao = ac.getBean("notebookDao",NotebookDao.class);
		noteDao = ac.getBean("noteDao",NoteDao.class);
	}
	@Test //测试MyBatis配置
	public void testMapperScanner(){
		Object obj = ac.getBean("dataSource");
		System.out.println(obj);
	}
	@Test //测试UserDao的saveUser方法
	public void testSaveUser(){
		System.out.println(dao);
		String id = UUID.randomUUID().toString();
		System.out.println(id);
		User user = new User(id,"Tom","123","","Tomcat");
		dao.saveUser(user);
	}
	@Test //测试FindUserById方法
	public void testFindUserById(){
		String id = "39295a3d-cc9b-42b4-b206-a2e7fab7e77c";
		User user = dao.findById(id);
		System.out.println(user);
	}
	@Test    //摘要知识讲解                                       //原数据和摘要摘要的原始形式是单向的，摘要的原始形式与Base64/Hex是双向的。
	public void testMd5(){
		
		try {
			//Message:消息  Digest:摘要    "MD5":算法名称（5是版本）
			//MessageDigest对象封装了复杂的消息摘要算法
			MessageDigest md=MessageDigest.getInstance("MD5");
			
			//update 提交数据，如果多次调用的话，就是对一堆数据进行摘要
//			md.update(0-99字节);
//			md.update(100-199字节);
//			md.update(200-299字节);。。。。。
//			byte[] 摘要=md.digest();          处理大数据  得到128位数              方式1
			
//			摘要=md.digest(数据);                处理大数据   得到128位数		方式2
			String pwd="123456";
			byte[] data=pwd.getBytes("utf-8");
			byte[] md5=md.digest(data);      //md5是128位数
//			for(byte b:md5){ 
//				System.out.println(b);
//			}
			/*
			 * 将byte数组（按每个byte转为2个16进制数）    转为16进制字符串
			 * （需要导包commons-codec）	 
			 * Mysql存储字符串和文本方便！  			
			 */
			String hex=Hex.encodeHexString(md5); 
			System.out.println(hex);
			/*
			 * Base64
			 * 将byte数组 转为文本
			 */
			String base64=Base64.encodeBase64String(md5);
			System.out.println(base64);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testSaltMd5(){
		String password="123";
		System.out.println(Md5.md5(password));
	}
	@Test
	public void testRegUser(){
		UserService svc = ac.getBean("userService",UserService.class);
		User user = svc.regist("lady", "123", "范传奇");
		System.out.println(user);
	}
	@Test
	public void testFindNotebookByUserId(){
		List<Map<String,Object>> list = 
				notebookDao.findNotebookByUserId("39295a3d-cc9b-42b4-b206-a2e7fab7e77c");
		for(Map<String,Object> map : list){
//		Map<String,Object> map = list.get(1);
			Set<Entry<String,Object>> set = map.entrySet();
			for(Entry<String,Object> s : set){
				System.out.println(s.getKey()+":"+s.getValue());
			}
		}
	}
	@Test
	public void testFindNoteByNotebookId(){
		List<Map<String,Object>> list = 
				noteDao.findNoteByNotebookId("d92e6b86-e48a-485d-8f11-04a93818bb42");
		for(Map<String,Object> map : list){
		/*Map<String,Object> map = list.get(1);*/
			Set<Entry<String,Object>> set = map.entrySet();
			for(Entry<String,Object> s : set){
				System.out.println(s.getKey()+":"+s.getValue());
			}
		}
	}
	@Test
	public void testUpdateNote(){
		String id = "09f60aeb-a573-4fcf-b39f-903e1536e762";
		Note note = noteDao.findNoteById(id);
		note.setTitle("云笔记项目测试");
		noteDao.updateNote(note);
		System.out.println(note.getTitle());
	}
	@Test
	public void testUpdateNoteDelType(){
		noteDao.updateNoteDelType(
				"046b0110-67f9-48c3-bef3-b0b23bda9d4e",
				"051538a6-0f8e-472c-8765-251a795bc88f",
				"054449b4-93d4-4f97-91cb-e0043fc4497f");
	}
	@Test
	public void testCountNormalNote(){
		List<String> list  = new ArrayList<String>();
		list.add("051538a6-0f8e-472c-8765-251a795bc88f");
		list.add("054449b4-93d4-4f97-91cb-e0043fc4497f");
		list.add("066af16d-183b-41ff-8a7c-8a8bd6bfccb4");
		int n = noteDao.countNormalNote(list);
		System.out.println(n);
	}
	@Test
	public void testUpdateNotes(){
		String[] ids = {"051538a6-0f8e-472c-8765-251a795bc88f",
				"054449b4-93d4-4f97-91cb-e0043fc4497f",
				"066af16d-183b-41ff-8a7c-8a8bd6bfccb4"};
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("typeId", 1);
		map.put("lastModifyTime", System.currentTimeMillis());
		map.put("ids", ids);
		noteDao.updateNotes(map);
	}
	@Test
	public void testCountNotes(){
		String[] ids = {"051538a6-0f8e-472c-8765-251a795bc88f",
				"054449b4-93d4-4f97-91cb-e0043fc4497f",
				"066af16d-183b-41ff-8a7c-8a8bd6bfccb4"};
		Map<String, Object> map = 
			new HashMap<String, Object>();
		map.put("typeId", "2");
		//map.put("lastModifyTime", System.currentTimeMillis());
		//map.put("ids", ids);
		Integer n=noteDao.countNotes(map);
		System.out.println(n);
		
	}
	@Test
	public void testMoveNotes(){
		NotebookService service =
				ac.getBean("noteBookService",NotebookService.class);
		service.deleteNotes("051538a6-0f8e-472c-8765-251a795bc88f",
				"054449b4-93d4-4f97-91cb-e0043fc4497f",
				"066af16d-183b-41ff-8a7c-8a8bd6bfccb4");
	}
	@Test
	public void testDeleteNotes(){
		noteDao.deleteNotes("051538a6-0f8e-472c-8765-251a795bc88f",
				"054449b4-93d4-4f97-91cb-e0043fc4497f",
				"066af16d-183b-41ff-8a7c-8a8bd6bfccb4");
	}
	@Test
	public void testFindByKeys(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("typeId", "1");
		map.put("title", "%aa%");
		map.put("body", "%aa%");
		List<Map<String,Object>> list = noteDao.findByKeys(map);
		for(Map<String,Object> note : list){
			System.out.println(note);
		}
	}
}
