package com.cbmachinery.aftercareserviceagent.user.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ClientChangePasswordDTO {
	
	private final String oldPassWord;
	private final String newPassWord;
	
	
	@JsonCreator
	public ClientChangePasswordDTO(@JsonProperty("oldPassWord") String oldPassWord,@JsonProperty("newPassWord") String newPassWord) {
		super();
		this.oldPassWord = oldPassWord;
		this.newPassWord = newPassWord;
	}
	
	

}
