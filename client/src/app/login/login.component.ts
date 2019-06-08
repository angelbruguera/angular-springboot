import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AuthenticationService } from '../_services/authentication.service';
import { SignIn } from '../_models/signin';
import { MessageService } from '../_services/message.service';
import { Role } from '../_models/role';

@Component({ templateUrl: 'login.component.html' })
export class LoginComponent implements OnInit {
  signinForm: FormGroup;
  signIn: SignIn;

  submitted = false;


  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private messageService: MessageService
  ) {
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit() {
    this.signinForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  /***
   * Agafa tots els camps
   * assignats als inputs del
   * formulari
   */
  get formData() { return this.signinForm.controls; }

  /**
   * Al trobar l'event submir
   * envia la petició a la API
   */
  onSubmit() {
    this.submitted = true;

    /**
     * Si el formulari
     * no té els Validators assignats
     * envia un missatge i no realitza la petició.
     */
    if (this.signinForm.invalid) {
      this.messageService.setMessage({ type: 'error', message: 'Formulari invalidat' });
      return;
    }

    this.signIn = new SignIn(this.formData.username.value, this.formData.password.value);

    /**
     * S'agafa l'objecte signIn
     * que hem instanciat anteriorment
     * i fem la petició al servidor
     * per loguejarse
     */
    this.authenticationService.login(this.signIn)
      .pipe(first())
      .subscribe(
        () => {
          if (this.authenticationService.currentUserValue.role === Role.BUSINESS) {
            this.router.navigate(['/me/questions']);
          } else {
            this.router.navigate(['/me/results']);
          }
        },
        (error) => {
          if (error.error.status === 401) {
            this.messageService.setMessage({ type: 'error', message: 'Usuari o contrasenya incorrectes' });
          } else { this.messageService.setMessage({ type: 'error', message: error.error.message }); }

        });
  }
}
