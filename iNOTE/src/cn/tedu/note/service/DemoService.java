package cn.tedu.note.service;

import java.util.List;

import cn.tedu.note.entity.User;

public interface DemoService {

	List<String> batchRegist(String... userName);

	User regist(String name, String password, String nick);

	List<String> batRegist(String... userName);

}