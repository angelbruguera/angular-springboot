package com.dispose.payload;

import javax.validation.constraints.NotBlank;

public class SkillRequest {
	
	@NotBlank
	private String skillId;
	
	@NotBlank
	private String skillName;
	
	@NotBlank
	private boolean hasImage;

	public String getSkillId() {
		return skillId;
	}


	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}


	public String getSkillName() {
		return skillName;
	}


	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	
	public boolean isHasImage() {
		return hasImage;
	}


	public void setHasImage(boolean hasImage) {
		this.hasImage = hasImage;
	}

}
