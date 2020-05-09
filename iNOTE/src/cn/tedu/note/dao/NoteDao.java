package cn.tedu.note.dao;

import java.util.List;
import java.util.Map;

import cn.tedu.note.entity.Note;

public interface NoteDao {
	List<Map<String, Object>>
		findNoteByNotebookId(
		String notebookId);
	
	Note findNoteById(String noteId);
	
	void addNote(Note note);
	
	void updateNote(Note note);
	
	void removeNote(Note note);
	
	void updateNoteDelType(String... ids);
	
	Integer countNormalNote(List<String> ids);
	/*
	 * 使用mao作为参数
	 * 批量更新Note
	 * map中可以保存多个属性
	 * map = {typeId:"2",
	 *        lastModifyTime:141211,
	 *        ids:['1','2','3']}
	 */
	void updateNotes(Map<String,Object> map);
	
	Integer countNotes(Map<String,Object> map);
	/*
	批量删除(移动笔记)
	*/
	void deleteNotes(String... ids);
	
	List<Map<String,Object>> findByKeys(Map<String,Object> map);
}





