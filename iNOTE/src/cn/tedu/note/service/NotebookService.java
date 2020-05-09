package cn.tedu.note.service;

import java.util.List;
import java.util.Map;

import cn.tedu.note.entity.Note;
import cn.tedu.note.entity.Notebook;

public interface NotebookService {
	
	String NORMAL_TYPE = "1";
	String DELETE_TYPE = "2";
	String DISABLE_TYPE = "3";
	String NORMAL_STATUS = "1";
	String FAVORITE_STATUS = "2";
	
	Notebook addNotebook(String notebookName,String userId,String desc);
	
	List<Map<String, Object>> listNotebooks(
			String userId);
	
	/**
	 * 查询笔记本中的全部笔记
	 */
	List<Map<String, Object>> listNotes(
			String notebookId); 
	
	Note loadNote(String noteId);
	
	/**
	 * 更新笔记的内容
	 * @param noteId 笔记的ID
	 * @param title 标题
	 * @param body  内容
	 */
	Note updateNoteBody(
		String noteId, String title, 
		String body);
	Note addNote(String title,String notebookId,String userId);
	//删除指定的笔记
	//返回已经删除的笔记对象
	Note deleteNote(String noteId);	
	
	Note moveNote(String noteId , String notebookId);
	
	void deleteNotes(String... ids);
}






