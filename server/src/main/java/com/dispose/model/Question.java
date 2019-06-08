package com.dispose.model;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@JsonIgnoreProperties(value = "respostaCorrecte", allowSetters = true)
public class Question implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonProperty("questionUser")
	private User questionUser;
	
	private String enunciat;
	
	private String resposta1;

	private String resposta2;
	
	private String resposta3;
	
	private String resposta4;
	
	@JsonProperty("respostaCorrecte")
	private String respostaCorrecte;
	
	@ManyToMany
	@JoinTable(name = "QuestionsResults",   
	        joinColumns = @JoinColumn(name = "result_id", referencedColumnName = "id"),   
	        inverseJoinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"),   
	        uniqueConstraints={@UniqueConstraint(columnNames={"result_id", "question_id"})})  
	@JsonIgnoreProperties("questions")
	private List<Result> results;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	private Skill skill;

	public Question() {
		
	}
	
	
	public Question(User questionUser, String enunciat, String resposta1, String resposta2, String resposta3,
			String resposta4, String respostaCorrecte) {
		super();
		this.questionUser = questionUser;
		this.enunciat = enunciat;
		this.resposta1 = resposta1;
		this.resposta2 = resposta2;
		this.resposta3 = resposta3;
		this.resposta4 = resposta4;
		this.respostaCorrecte = respostaCorrecte;
		
	}
	
	

	public Question(User questionUser, String enunciat, String resposta1, String resposta2, String resposta3,
			String resposta4, String respostaCorrecte, Skill skill) {
		super();
		this.questionUser = questionUser;
		this.enunciat = enunciat;
		this.resposta1 = resposta1;
		this.resposta2 = resposta2;
		this.resposta3 = resposta3;
		this.resposta4 = resposta4;
		this.respostaCorrecte = respostaCorrecte;
		
		this.skill = skill;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getQuestionUser() {
		return questionUser;
	}
	
	public String getQuestionUsername() {
		return questionUser.getUsername();
	}
	
	public String getQuestionProfile() {
		return questionUser.getProfileURL();
	}

	public void setQuestionUser(User questionUser) {
		this.questionUser = questionUser;
	}

	public String getEnunciat() {
		return enunciat;
	}

	public void setEnunciat(String enunciat) {
		this.enunciat = enunciat;
	}

	public String getResposta1() {
		return resposta1;
	}

	public void setResposta1(String resposta1) {
		this.resposta1 = resposta1;
	}

	public String getResposta2() {
		return resposta2;
	}

	public void setResposta2(String resposta2) {
		this.resposta2 = resposta2;
	}

	public String getResposta3() {
		return resposta3;
	}

	public void setResposta3(String resposta3) {
		this.resposta3 = resposta3;
	}

	public String getResposta4() {
		return resposta4;
	}

	public void setResposta4(String resposta4) {
		this.resposta4 = resposta4;
	}

	public String getRespostaCorrecte() {
		return respostaCorrecte;
	}

	public void setRespostaCorrecte(String respostaCorrecte) {
		this.respostaCorrecte = respostaCorrecte;
	}
	

	public Skill getSkill() {
		return skill;
	}
	
	public String getSkillImage() {
		return skill.getUrlImage();
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	
	
	
	

}
