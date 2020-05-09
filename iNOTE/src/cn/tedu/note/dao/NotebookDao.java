package cn.tedu.note.dao;

import java.util.List;
import java.util.Map;

import cn.tedu.note.entity.Notebook;

public interface NotebookDao {
	
	public List<Map<String, Object>> 
		findNotebookByUserId(String userId);
	public void addNotebook(Notebook notebook);
	public void deleteNotebook(String notebookId);
}
