package com.vitamindz.ims.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class ResultObject {
	
	private long ResponseCode =0;
	private String ResponseMessage=null;
	private Object ResponseObject=null;
	
	public ResultObject() {
		
	}
	
	public ResultObject(long responseCode) {
		super();
		ResponseCode = responseCode;
	}
	public ResultObject(long responseCode, String responseMessage) {
		super();
		ResponseCode = responseCode;
		ResponseMessage = responseMessage;
	}
	public ResultObject(long responseCode, String responseMessage, Object responseObject) {
		super();
		ResponseCode = responseCode;
		ResponseMessage = responseMessage;
		ResponseObject = responseObject;
	}
	public long getResponseCode() {
		return ResponseCode;
	}
	public void setResponseCode(long responseCode) {
		ResponseCode = responseCode;
	}
	public String getResponseMessage() {
		return ResponseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		ResponseMessage = responseMessage;
	}
	public Object getResponseObject() {
		return ResponseObject;
	}
	public void setResponseObject(Object responseObject) {
		ResponseObject = responseObject;
	}
	@Override
	public String toString() {
		return "ResultObject [ResponseCode=" + ResponseCode + ", ResponseMessage=" + ResponseMessage
				+ ", ResponseObject=" + ResponseObject + "]";
	}
	
	public void setmessageandcode(long code,String msg){
		this.ResponseCode=code;
		this.ResponseMessage=msg;
	}

}
