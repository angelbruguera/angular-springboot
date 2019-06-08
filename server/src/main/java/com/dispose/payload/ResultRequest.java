package com.dispose.payload;

import javax.validation.constraints.NotBlank;

public class ResultRequest {

	@NotBlank
	private String skill;

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}
	
	
	
}
