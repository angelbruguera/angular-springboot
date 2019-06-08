import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { User } from '../_models/user';
import { Globals } from '../variables';
import { SignIn } from '../_models/signin';
import { SignUp } from '../_models/signup';
import { Role } from '../_models/role';
import * as moment from 'moment';


/**
 * Aquest servei basicament centralitza
 * tota la informació d'accés del usuari
 * i s'encarga de controlar les modificacions
 * del usuari envers l'autentificació
 */
@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<User>;
    public currentUser: Observable<User>;

    /**
     * Per tenir accesibilitat i persistencia
     * del usuari en el entorn client hem de
     * crear un registre al localStorage amb el
     * token, la data d'expiració, informació del
     * usuari, etc.
     * 
     */
    constructor(private http: HttpClient) {
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
    }

    /**
     * Aquest metode agafa
     * constantment el objecte User
     * que s'ha emmagatzemat al localStorage 
     */
    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }

    /**
     * Aquesta funció comproba
     * que el objecte User està assignat
     * i si la data del token no ha expirat.
     * 
     * Retorna un boolean.
     * 
     * Aquesta funció servirà per modificar el comportament
     * de l'aplicació.
     */
    isAuthenticated() {
        return this.currentUserValue && new Date() < moment(this.currentUserValue.expire.toString(), 'YYYY-MM-DD HH:mm:ss').toDate();
    }


    /**
     * Aquesta funció retorna un boolean
     * segons si és candidat o business.
     * 
     * Ens ajudarà a modificar certs
     * aspectes diferenciats entre
     * els diferents rols
     */
    isCandidate(): boolean {
        return this.currentUserValue.role === Role.CANDIDATE;
    }

    /**
     * Aquesta funció és el fonamental
     * per l'aplicació ja que s'encarrega
     * de assignar el token al localStorage
     * i així variar el comportament de l'aplicació
     * 
     */
    login(signIn: SignIn) {
        return this.http.post<any>(`${Globals.url}/auth/signin`, signIn)
            .pipe(map(user => {
                if (user && user.token) {
                    localStorage.setItem('currentUser', JSON.stringify(user));
                    this.currentUserSubject.next(user);
                }

                return user;
            }));
    }

    /**
     * 
     * Aquesta funció registra un usuari a la base de dades
     */
    signup(signUp: SignUp) {
        return this.http.post<any>(`${Globals.url}/auth/signup`, signUp);
    }

    /**
     * Aquesta funció borra el token i li assigna al observable un valor null
     * per que l'aplicació no detecti el objecte User assignat al fer login
     */
    logout() {

        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
    }
}