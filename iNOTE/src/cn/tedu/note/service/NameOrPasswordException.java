package cn.tedu.note.service;

/*
 * 继承RuntimeException  是现在的语法规范
 */
public class NameOrPasswordException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

	public NameOrPasswordException() {
		// TODO Auto-generated constructor stub
	}

	public NameOrPasswordException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NameOrPasswordException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public NameOrPasswordException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NameOrPasswordException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
