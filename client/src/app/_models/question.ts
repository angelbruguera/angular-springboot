export class Question {
    id: number;
    enunciat: string;
    resposta1: string;
    resposta2: string;
    resposta3: string;
    resposta4: string;
    respostaCorrecta: string;
    skill: string;
    skillImage: string;

    constructor(e?, r1?, r2?, r3?, r4?, rc?, s?) {
        this.enunciat = e;
        this.resposta1 = r1;
        this.resposta2 = r2;
        this.resposta3 = r3;
        this.resposta4 = r4;
        this.respostaCorrecta = rc;
        this.skill = s;
    }


}