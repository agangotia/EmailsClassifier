package com.anupam.email;

import java.util.Date;

import javax.mail.Address;
/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */

/**
 * Class: email
 * This is the VO {value object} for email
 */
public class email {
	private String subject;
	private String sender;
	private Date date;
	private String content;
	private Address[] cc;
	
	private Address[] bcc;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Address[] getCc() {
		return cc;
	}
	public void setCc(Address[] cc) {
		this.cc = cc;
	}
	public Address[] getBcc() {
		return bcc;
	}
	public void setBcc(Address[] bcc) {
		this.bcc = bcc;
	}
	public String returnText(){
		return this.subject+this.content;
	}
}
