package com.dispose.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	private Result result;
	
	private Long idQuestion;
	
	private String respostaSeleccionada;
	
	@Column(name = "correcte", nullable = false)
	private boolean isCorrecte = false;
	
	public Answer() {}
	
	public Answer(Result result,Long idQuestion, String respostaSeleccionada) 
	{
		this.idQuestion=idQuestion;
		this.respostaSeleccionada=respostaSeleccionada;
		this.result = result;
	}
	
	public String getRespostaSeleccionada() {
		return respostaSeleccionada;
	}

	public void setRespostaSeleccionada(String respostaSeleccionada) {
		this.respostaSeleccionada = respostaSeleccionada;
	}

	public boolean isCorrecte() {
		return isCorrecte;
	}

	public void setCorrecte(boolean isCorrecte) {
		this.isCorrecte = isCorrecte;
	}
	
	public void setIdQuestion(Long idQuestion) {
		this.idQuestion = idQuestion;
	}
	
	public Long getIdQuestion() {
		return this.idQuestion;
	}

	
	

}
