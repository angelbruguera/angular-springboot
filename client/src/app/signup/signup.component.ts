import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SignUp } from '../_models/signup';
import { Router } from '@angular/router';
import { AuthenticationService } from '../_services/authentication.service';
import { SignIn } from '../_models/signin';
import { first } from 'rxjs/operators';
import { MessageService } from '../_services/message.service';
import { Role } from '../_models/role';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signupForm: FormGroup;
  signUp: SignUp;
  signIn: SignIn;
  error: string;
  role = 'candidate';
  submitted = false;
  isCandidate = true;


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
    this.signupForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(25)]],
      password: ['', [Validators.required, Validators.maxLength(100)]],
      email: ['', [Validators.required, Validators.email, Validators.maxLength(40)]],
      name: ['', [Validators.required, Validators.maxLength(40)]],

    });
  }

  /**
   * Aquesta funció
   * defineix quin rol serà
   * l'usuari que crearem
   */
  setRole() {
    if (!this.isCandidate) {
      this.role = 'candidate';
    } else { this.role = 'business'; }
    this.isCandidate = !this.isCandidate;
  }

  get formData() { return this.signupForm.controls; }

  /**
   * Aquesta funció s'assigna al event
   * submit del formulari i envia a la API
   * la informació del usuari que crearem
   */
  onSubmit() {
    this.submitted = true;

    if (this.signupForm.invalid) {

      this.messageService.setMessage({ type: 'error', message: 'Formulari invalidat' });
      return;
    }

    this.signUp = new SignUp(this.formData.username.value,
      this.formData.email.value,
      this.formData.password.value,
      this.role,
      this.formData.name.value
    );

    this.signIn = new SignIn(this.formData.username.value, this.formData.password.value);

    this.authenticationService.signup(this.signUp)
      .subscribe(
        () => {
          this.authenticationService.login(this.signIn)
            .pipe(first())
            .subscribe(
              () => {
                if (this.authenticationService.currentUserValue.role === Role.BUSINESS) {
                  this.router.navigate(['/me/questions']);
                } else {
                  this.router.navigate(['/me/results']);
                }

              });
        },
        error => {
          this.messageService.setMessage({ type: 'error', message: error.error.message });

        });
  }
}
