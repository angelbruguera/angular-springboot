import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Practice } from '../_models/practice';

import { Globals } from '../variables';
import { Profile } from '../_models/profile';
import { Question } from '../_models/question';

/**
 * Aquesta classe s'encarga de
 * la informació del perfil
 * del usuari
 */
@Injectable({ providedIn: 'root' })
export class ProfileService {

    constructor(private http: HttpClient) { }

    /**
     * Aquesta funció agafa la informació
     * del usuari
     */
    getProfile() {
        return this.http.get<Profile>(`${Globals.url}/users/me`);
    }

    /**
     * Aquesta funció agafa totes les
     * preguntes relacionades amb l'usuari
     */
    getQuestions() {
        return this.http.get<Question[]>(`${Globals.url}/users/me/questions`);
    }

    /**
     * Aquesta funció agafa totes les
     * pràctiques relacionades amb l'usuari
     */
    getPractice() {
        return this.http.get<Practice[]>(`${Globals.url}/users/me/results`);
    }

    /**
     * Aquesta funció borra l'usuari
     *
     * No té utilitat en l'aplicació peró
     * vaig pensar en implementar-la al principi
     */
    removeUser() {
        return this.http.delete<any>(`${Globals.url}/users/me`);
    }


}
