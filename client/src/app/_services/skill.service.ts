import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Globals } from '../variables';

import { Skill } from '../_models/skill';

/**
 * Aquesta classe aplica alguns metodes
 * de la API en relació a les skills.
 * 
 * Al ser definides per l'administrador,
 * no he vist necessari implementar els
 * metodes CRUD.
 */
@Injectable({ providedIn: 'root' })
export class SkillService {
    constructor(private http: HttpClient) { }

    /**
     * Aquesta funció agafa totes les skills
     */
    getAllSkills() {
        return this.http.get<Skill[]>(`${Globals.url}/skills`);
    }

}