package com.watsoo.device.management.dto;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_NULL)
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Response<T> {

	private int responseCode;
	private String message;
	private String requestedURI;
	private String requestCode;
	private T data;

    public Response ( ) {

    }


    public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getRequestedURI() {
		return requestedURI;
	}

	public void setRequestedURI(String requestedURI) {
		this.requestedURI = requestedURI;
	}

	public Response(HttpStatus notFound, String iccidNotFound) {
		super();

	}

	public Response(int responseCode, String message, T data) {
		super();
		this.responseCode = responseCode;
		this.message = message;
		this.data = data;
	}

	public Response(int responseCode, String message) {
		super();
		this.responseCode = responseCode;
		this.message = message;
	}

	public Response(int responseCode, T data, String message, String requestCode) {
		this.responseCode = responseCode;
		this.data = data;
		this.message = message;
		this.requestCode = requestCode;
	}
}