package com.dispose.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.dispose.payload.SkillRequest;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table( uniqueConstraints = @UniqueConstraint(columnNames = {"skill_id"}))
public class Skill implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@Column(name="skill_id")
	private String skillId;
	
	private String skillName;
	
	private boolean hasImage;
	
	private String urlImage;
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "skill", cascade = CascadeType.ALL)
	private List<Question> questions;
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "skill", cascade = CascadeType.ALL)
	private List<Result> results;
	
	public Skill() {}
	
	public Skill(String skillId,String skillName, boolean hasImage) {
		this.skillId = skillId;
		this.skillName = skillName;
		this.hasImage = hasImage;
	}

	public Skill(String skillId,String skillName, String urlImage, boolean hasImage) {
		this.skillId = skillId;
		this.skillName = skillName;
		this.hasImage = hasImage;
	}
	
	public Skill(SkillRequest skill, String urlImage) {
		this.skillId = skill.getSkillId();
		this.skillName = skill.getSkillName();
		this.hasImage = skill.isHasImage();
		this.urlImage = urlImage;
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
	


	public void setUrlImage(String url) {
		
		this.urlImage = url;
	
	}

	public boolean isHasImage() {
		return hasImage;
	}

	public void setHasImage(boolean hasImage) {
		this.hasImage = hasImage;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}
	
}
