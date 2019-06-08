import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Question } from '../_models/question';

import { Globals } from '../variables';

/**
 * Aquesta classe aplica 
 * alguns metodes CRUD de la API
 * en relació a la creació de preguntes
 */
@Injectable({ providedIn: 'root' })
export class QuestionService {
    constructor(private http: HttpClient) { }


    /**
     * Aquesta funció crea
     * una pregunta
     */
    createQuestion(question: Question) {

        return this.http.post<any>(`${Globals.url}/questions`, question);
    }

    /**
     * Aquesta funció borra
     * una pregunta
     */
    deleteQuestion(id: number) {
        return this.http.delete<Question>(`${Globals.url}/questions/ids/${id}`);
    }

}
