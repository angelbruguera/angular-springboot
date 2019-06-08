package com.dispose.payload;

import javax.validation.constraints.NotBlank;

public class AnswerRequest {

	private Long idQuestion;
	
	@NotBlank
	private String respostaSeleccionada;

	public String getRespostaSeleccionada() {
		return respostaSeleccionada;
	}

	public void setRespostaSeleccionada(String respostaSeleccionada) {
		this.respostaSeleccionada = respostaSeleccionada;
	}
	
	public void setIdQuestion(Long idQuestion) {
		this.idQuestion = idQuestion;
	}
	
	public Long getIdQuestion() {
		return this.idQuestion;
	}
	
	
}
