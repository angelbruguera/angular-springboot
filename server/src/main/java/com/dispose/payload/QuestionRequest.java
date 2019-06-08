package com.dispose.payload;

import javax.validation.constraints.NotBlank;

public class QuestionRequest {
	@NotBlank
    private String enunciat;

    @NotBlank
    private String resposta1;
    
    @NotBlank
    private String resposta2;
    
    @NotBlank
    private String resposta3;
    
    @NotBlank
    private String resposta4;
    
    @NotBlank
    private String respostaCorrecta;
    
    @NotBlank
    private String skill;

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

	public String getRespostaCorrecta() {
		return respostaCorrecta;
	}

	public void setRespostaCorrecta(String respostaCorrecta) {
		this.respostaCorrecta = respostaCorrecta;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

    

}
