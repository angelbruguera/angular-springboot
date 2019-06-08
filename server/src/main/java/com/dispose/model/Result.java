package com.dispose.model;

import java.io.Serializable;

import java.util.List;
import javax.persistence.UniqueConstraint;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
 
@Entity
@JsonIgnoreProperties(value = "answers",allowGetters = true)
public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name="candidateId")
	private User candidate;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name="skillId")
	private Skill skill;
	
	@ManyToMany(cascade = {CascadeType.MERGE})  
	@JoinTable(name = "QuestionsResults",   
	        joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"),   
	        inverseJoinColumns = @JoinColumn(name = "result_id", referencedColumnName = "id"),   
	        uniqueConstraints={@UniqueConstraint(columnNames={"question_id", "result_id"})})  
	@JsonIgnoreProperties("results")
	private List<Question> questions;
	
	@JsonProperty("answers")
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "result")
    private List<Answer> answers;
	
	private int preguntes;
	
	private int respostesContestades;
	
	private int respostesCorrectes;
	
	public Result() {}

	public Result(User userSelected, Skill skill, List<Question> questions,int preguntes, int respostesContestades,int respostesCorrectes) {
		this.candidate = userSelected;
		this.skill = skill;
		this.questions = questions;
		this.setPreguntes(preguntes);
		this.setRespostesContestades(respostesContestades);
		this.setRespostesCorrectes(respostesCorrectes);
	}
	
	public Result(Long id,User userSelected, Skill skill, List<Question> questions,int preguntes, int respostesContestades,int respostesCorrectes) {
		this.setId(id);
		this.candidate = userSelected;
		this.skill = skill;
		this.setQuestions(questions);
		this.setPreguntes(preguntes);
		this.setRespostesContestades(respostesContestades);
		this.setRespostesCorrectes(respostesCorrectes);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getCandidate() {
		return candidate;
	}
	
	public String getCandidateUsername() {
		return candidate.getUsername();
	}

	public void setCandidate(User candidate) {
		this.candidate = candidate;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	
	public String getSkillImage() {
		return skill.getUrlImage();
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public int getPreguntes() {
		return preguntes;
	}

	public void setPreguntes(int preguntes) {
		this.preguntes = preguntes;
	}

	public int getRespostesContestades() {
		return respostesContestades;
	}

	public void setRespostesContestades(int respostesContestades) {
		this.respostesContestades = respostesContestades;
	}

	public int getRespostesCorrectes() {
		return respostesCorrectes;
	}

	public void setRespostesCorrectes(int respostesCorrectes) {
		this.respostesCorrectes = respostesCorrectes;
	}

	@Transactional
	public int getPorcentatge() {
		return (this.respostesCorrectes*100) / this.preguntes;
	}	
	
}
