import { Injectable } from '@angular/core';

import { BehaviorSubject, Observable } from 'rxjs';
import { Loading } from '../_models/loading';

/**
 * Aquesta classe definirà el comportament
 * del loading a l'hora de realitzar una carga
 * de la pagina
 */
@Injectable({ providedIn: 'root' })
export class LoadingService {
    private currentLoadingSubject: BehaviorSubject<Loading>;
    public currentLoading: Observable<Loading>;
    private setLoadingScreen;
    private countLoading = 0;
    constructor() {
        this.currentLoadingSubject = new BehaviorSubject<Loading>(JSON.parse(localStorage.getItem('currentLoading')));
        this.currentLoading = this.currentLoadingSubject.asObservable();
    }

    /**
     * Aquest metode agarà el objecte Loading
     * assignat al localStorage
     */
    public get currentLoadingValue(): Loading {
        return this.currentLoadingSubject.value;
    }

    /****
     * Aquesta funció assignarà una pantalla
     * de recarga mitjançant el missatge que
     * se l'hi otorgui al objecte Loading 
     * i la cantitat de segons que serà 
     * la durada de la pantalla de carga
     */
    setLoading(loading: Loading) {

        localStorage.setItem('currentLoading', JSON.stringify(loading));
        this.currentLoadingSubject.next(loading);
        this.setLoadingScreen = setInterval(() => {
            this.countLoading++;
            if (this.countLoading > loading.seconds) {
                this.countLoading = 0;
                localStorage.removeItem('currentLoading');
                this.currentLoadingSubject.next(null);
                clearInterval(this.setLoadingScreen);
            }
        }, 1000);
    }

}