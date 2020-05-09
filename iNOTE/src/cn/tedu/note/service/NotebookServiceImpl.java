package cn.tedu.note.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.note.dao.NoteDao;
import cn.tedu.note.dao.NotebookDao;
import cn.tedu.note.entity.Note;
import cn.tedu.note.entity.Notebook;

@Service("notebookService")
public class NotebookServiceImpl
	implements NotebookService{
	
	@Autowired
	private NotebookDao notebookDao;
	
	@Autowired
	private NoteDao noteDao;
	
	public List<Map<String, Object>> 
		listNotebooks(String userId) {
		if(userId==null||userId.trim().isEmpty()){
			throw new ServiceException(
					"userId不能为空");
		}
		return notebookDao
			.findNotebookByUserId(userId);
	}
	
	
	public List<Map<String, Object>> 
		listNotes(String notebookId) {
		if(notebookId==null){
			throw new ServiceException("笔记本ID不能为空");
		}
		return noteDao.findNoteByNotebookId(notebookId);
	}
	
	public Note loadNote(String noteId) {
		if(noteId==null){
			throw new ServiceException("笔记id不能为空");
		}
		return noteDao.findNoteById(noteId);
	}
	
	public Note updateNoteBody(
		String noteId, String title, String body) {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new ServiceException("ID不能为空");
		}
		if(title==null){
			title = "";
		}
		if(body==null){
			body = "";
		}
		//找到Note信息
		Note note=noteDao.findNoteById(noteId);
		if(note == null){
			throw new ServiceException("没有笔记信息");
		}
		if(! title.trim().isEmpty()){
			note.setTitle(title); 
		}
		note.setBody(body); 
		long now=System.currentTimeMillis();
		note.setLastModifyTime(now);
		noteDao.updateNote(note); 
		return note;
	}
	public Note addNote(String title,String notebookId,String userId){
		if(title==null || title.trim().isEmpty()){
			title="未命名笔记";
		}
		if(notebookId==null || notebookId.trim().isEmpty()){
			throw new ServiceException("必须选择笔记本");
		}
		if(userId==null || userId.trim().isEmpty()){
			throw new ServiceException("必须登录");
		}
		String id = UUID.randomUUID().toString();
		Note note = new Note();
		long now = System.currentTimeMillis();
		note.setId(id);
		note.setBody("");
		note.setCreateTime(now);
		note.setLastModifyTime(now);
		note.setNotebookId(notebookId);
		note.setTitle(title);
		note.setUserId(userId);
		//是否被收藏的笔记
		note.setStatusId(NORMAL_STATUS);
		//是否被删除的笔记
		note.setTypeId(NORMAL_TYPE);
		noteDao.addNote(note);
		return note;
	}


	@Override
	public Note deleteNote(String noteId) {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new ServiceException("笔记ID不能为空！");
		}
		Note note = noteDao.findNoteById(noteId);
		if(note == null){
			throw new ServiceException("ID不存在！");
		}
		//System.out.println(note.getTypeId());
		if(note.getTypeId().equals(NORMAL_TYPE)){
			note.setTypeId(DELETE_TYPE);
			long now = System.currentTimeMillis();
			note.setLastModifyTime(now);
			noteDao.updateNote(note);
			return note;
		}
		throw new ServiceException("只能删除正常笔记");
	}


	@Override
	public Note moveNote(String noteId, String notebookId) {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new ServiceException("noteId不能为空");
		}
		if(notebookId==null || notebookId.trim().isEmpty()){
			throw new ServiceException("notebookId不能为空");
		}
		Note note = noteDao.findNoteById(noteId);
		note.setNotebookId(notebookId);
		noteDao.updateNote(note);
		return note;
	}
	//NotebookServiceImpl中实现业务方法
	@Transactional
	public void deleteNotes(String... ids){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("typeId", NORMAL_TYPE);
		map.put("ids", ids);
		//检查正常笔记数量
		Integer n = noteDao.countNotes(map);
		if(n!=ids.length){
			throw new ServiceException("要删除笔记已被删除");
		}
		map.put("typeId", DELETE_TYPE);
		noteDao.updateNotes(map);
	}

	/*private String notebookId;
	private String userId;
	private String notebookTypeId;
	private String notebookName;
	private String notebookDesc;
	private Date notebookCreateTime;*/
	
	public Notebook addNotebook(String notebookName, String userId,String desc) {
		if(notebookName==null || notebookName.trim().isEmpty()){
			throw new ServiceException("笔记本名不能为空！");
		}
		if(userId==null || userId.trim().isEmpty()){
			throw new ServiceException("必须登录");
		}
		String notebookId = UUID.randomUUID().toString();
		Date notebookCreateTime = new Date();
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.format(now);*/
		Notebook notebook = new Notebook();
		notebook.setNotebookId(notebookId);
		notebook.setUserId(userId);
		notebook.setNotebookName(notebookName);
		notebook.setNotebookCreateTime(notebookCreateTime);
		notebook.setNotebookTypeId("5");
		notebook.setNotebookDesc(desc);
		notebookDao.addNotebook(notebook);
		return notebook;
	}
}



