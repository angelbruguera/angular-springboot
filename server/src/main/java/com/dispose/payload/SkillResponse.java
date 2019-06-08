package com.dispose.payload;

public class SkillResponse {

	private long id;
	
	private String skillId;
	
	private String skillName;
	
	private String urlImage;
	
	private boolean hasImage;
	
	private int totalQuestions;
	
	public int totalResults;
	
	public SkillResponse() {}
	
	public SkillResponse(long id, String skillId, String skillName, String urlImage,boolean hasImage, int totalQuestions, int totalResults) {
		this.id = id;
		this.skillId = skillId;
		this.skillName = skillName;
		this.urlImage = urlImage;
		this.hasImage = hasImage;
		this.setTotalQuestions(totalQuestions);
		this.setTotalResults(totalResults);
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public boolean isHasImage() {
		return hasImage;
	}

	public void setHasImage(boolean hasImage) {
		this.hasImage = hasImage;
	}

	public int getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
	
	
}
