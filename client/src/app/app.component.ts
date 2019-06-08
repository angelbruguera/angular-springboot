import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './_services/authentication.service';
import { User } from './_models/user';
import * as moment from 'moment';
import { Router } from '@angular/router';
import { Message } from './_models/message';
import { MessageService } from './_services/message.service';

@Component({ selector: 'app', templateUrl: 'app.component.html' })
export class AppComponent implements OnInit {
  currentUser: User;
  message: Message;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private messageService: MessageService,
  ) {
  }

  ngOnInit() {
    this.authenticationService.currentUser.subscribe((data) => { this.currentUser = data; });
    this.messageService.currentMessage.subscribe((data) => { this.message = data; });
  }

  isAuthenticated() {

    return this.authenticationService.isAuthenticated();
  }

  isCandidate() {
    return this.authenticationService.isCandidate();
  }

  removeMessage() {
    this.messageService.removeMessage();
  }

  logout() {
    this.authenticationService.logout();
    this.currentUser = null;
    this.router.navigate(['/login']);
  }
}