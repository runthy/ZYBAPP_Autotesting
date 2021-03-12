package com.yjg.appui.exception;
/**
 * 重试异常
 * 
 */
public class RetryException extends Exception{
	
	/**
	 * @param string
	 */
	public RetryException(String string){
		super(string);
	}
	/**
	 * @param message
	 * @param cause
	 */
	public RetryException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * @param cause
     */
    public RetryException(Throwable cause) {
        super(cause);
    }
}