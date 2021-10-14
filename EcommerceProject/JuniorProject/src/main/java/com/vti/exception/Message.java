package com.vti.exception;

/**
 * This class is Message
 * 
 * @Description:
 * @author: KienTT
 * @create_date: Sep 14, 2021
 * @version: 1.0
 * @modifer: KienTT
 * @modifer_date: Sep 14, 2021
 */
public class Message extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public Message(String message) {
		super(message);
	}

	public Message(Exception e) {
		super(e);
	}

}
