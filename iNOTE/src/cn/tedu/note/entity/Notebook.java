package cn.tedu.note.entity;

import java.io.Serializable;
import java.util.Date;

public class Notebook implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String notebookId;
	private String userId;
	private String notebookTypeId;//1:收藏 2:回收站 3:活动 4:推送 5:正常
	private String notebookName;
	private String notebookDesc;
	private Date notebookCreateTime;
	
	public String getNotebookId() {
		return notebookId;
	}
	public String getUserId() {
		return userId;
	}
	public String getNotebookTypeId() {
		return notebookTypeId;
	}
	public String getNotebookName() {
		return notebookName;
	}
	public String getNotebookDesc() {
		return notebookDesc;
	}
	public Date getNotebookCreateTime() {
		return notebookCreateTime;
	}
	public void setNotebookId(String notebookId) {
		this.notebookId = notebookId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setNotebookTypeId(String notebookTypeId) {
		this.notebookTypeId = notebookTypeId;
	}
	public void setNotebookName(String notebookName) {
		this.notebookName = notebookName;
	}
	public void setNotebookDesc(String notebookDesc) {
		this.notebookDesc = notebookDesc;
	}
	public void setNotebookCreateTime(Date notebookCreateTime) {
		this.notebookCreateTime = notebookCreateTime;
	}
}
