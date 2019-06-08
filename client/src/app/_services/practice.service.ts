import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Globals } from '../variables';
import { Practice } from '../_models/practice';

/***
 * Aquesta classe
 * s'encarga d'interactuar
 * amb els metodes CRUD de la
 * API definits en relació 
 * a les Practiques
 */
@Injectable({ providedIn: 'root' })
export class PracticeService {
    constructor(private http: HttpClient) { }


    /**
     * Aquesta funció crea una pràctica
     */
    createPractice(skillName: string) {

        return this.http.post<Practice>(`${Globals.url}/results`, { skill: skillName });
    }

    /**
     * 
     * Aquesta funció respon una pregunta de la pràctica
     */
    postAnswer(skill: string, respostaSeleccionada: string, idQuestion) {
        return this.http.post<string>(`${Globals.url}/results/${skill}`, { idQuestion, respostaSeleccionada });
    }

    /**
     * Aquesta funció agafa totes les pràctiques
     * relacionades a una skill
     * 
     */
    getPracticesBySkill(skill: string) {
        return this.http.get<Practice[]>(`${Globals.url}/skills/${skill}/results`);
    }

    /**
     * Aquesta funció borra una pràctica
     */
    deletePractice(id: number) {
        return this.http.delete<Practice>(`${Globals.url}/results/ids/${id}`);
    }

}