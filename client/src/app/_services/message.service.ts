import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Message } from '../_models/message';


/**
 * Aquesta classe centralitza
 * els missatges de tots els components
 * en el component Message
 */
@Injectable({ providedIn: 'root' })
export class MessageService {
    private currentMessageSubject: BehaviorSubject<Message>;
    public currentMessage: Observable<Message>;
    private setMessageScreen;
    private countMessage = 0;
    constructor() {
        this.currentMessageSubject = new BehaviorSubject<Message>(JSON.parse(localStorage.getItem('currentMessage')));
        this.currentMessage = this.currentMessageSubject.asObservable();
    }

    /**
     * Aquesta funció agafa el objecte
     * Message assignat al localStorage
     */
    public get currentMessageValue(): Message {
        return this.currentMessageSubject.value;
    }

    /****
     * Aquesta funció assignarà un missatge que
     * se l'hi definirà al objecte Message
     * i la cantitat de segons que serà
     * la durada del missatge
     */
    setMessage(message: Message) {
        localStorage.setItem('currentMessage', JSON.stringify(message));
        this.currentMessageSubject.next(message);
        this.setMessageScreen = setInterval(() => {
            this.countMessage++;
            if (this.countMessage > 5) {
                this.countMessage = 0;
                localStorage.removeItem('currentMessage');
                this.currentMessageSubject.next(null);
                clearInterval(this.setMessageScreen);
            }
        }, 1000);
    }

    /**
     * Aquesta funció borra el missatge
     */
    removeMessage() {
        localStorage.removeItem('currentMessage');
        this.currentMessageSubject.next(null);
    }

}