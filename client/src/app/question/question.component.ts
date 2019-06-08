import { Component, OnInit } from '@angular/core';
import { Skill } from '../_models/skill';
import { SkillService } from '../_services/skill.service';
import { FormGroup, FormBuilder, Validators, FormGroupDirective } from '@angular/forms';
import { Question } from '../_models/question';
import { QuestionService } from '../_services/question.service';
import { AuthenticationService } from '../_services/authentication.service';
import { User } from '../_models/user';
import { setLeft, setRight } from '../_functions/functions';
import { MessageService } from '../_services/message.service';
import { LoadingService } from '../_services/loading.service';
import { Loading } from '../_models/loading';
import { ProfileService } from '../_services/profile.service';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {

  questionForm: FormGroup;
  principalUser: User;
  skills: Skill[];
  skillSeleccionada: Skill;
  loading: Loading;
  respostaCorrecta: string;
  numRespostaCorrecta: number;
  numSkill = 0;
  count = 0;
  submitted = false;




  constructor(
    private authenticationService: AuthenticationService,
    private skillService: SkillService,
    private formBuilder: FormBuilder,
    private questionService: QuestionService,
    private messageService: MessageService,
    private loadingService: LoadingService,
  ) {
  }

  ngOnInit() {
    this.principalUser = this.authenticationService.currentUserValue;
    this.loadingService.setLoading({ information: 'Un moment, ' + this.principalUser.name, seconds: 3 });
    this.questionForm = this.formBuilder.group({
      enunciat: ['', [Validators.required, Validators.maxLength(200)]],
      resposta1: ['', [Validators.required, Validators.maxLength(75)]],
      resposta2: ['', [Validators.required, Validators.maxLength(75)]],
      resposta3: ['', [Validators.required, Validators.maxLength(75)]],
      resposta4: ['', [Validators.required, Validators.maxLength(75)]],

    });
    this.skillService.getAllSkills().subscribe((data) => { this.skills = data; });
  }

  /***
   * Agafa tots els camps
   * assignats als inputs del
   * formulari
   */
  get formData() { return this.questionForm.controls; }

  /**
   * Aquesta funció determinarà
   * quina resposta vols que sigui la
   * correcta
   */
  setRespostaCorrecta(value: string, num: number) {
    this.numRespostaCorrecta = num;
    this.respostaCorrecta = value;
  }

  /**
   * Aquesta funció s'assinga al
   * event click del botó de l'esquerra
   * on implementem la funció
   * global setLeft()
   */
  goLeft() {
    this.numSkill = setLeft(this.numSkill, this.skills.length);
  }

  /**
   * Aquesta funció s'assinga al
   * event click del botó de la dreta
   * on implementem la funció
   * global setRight()
   */
  goRight() {
    this.numSkill = setRight(this.numSkill, this.skills.length);
  }

  /**
   * Aquesta funció s'assigna al event
   * submit del formulari i envia a la API
   * el contingut de la pregunta i la resposta seleccionada
   */
  onSubmit(formDirective: FormGroupDirective) {
    this.submitted = true;
    if (this.questionForm.invalid) {
      this.messageService.setMessage({ type: 'error', message: 'Formulari invalidat' });
      return;
    }

    this.skillSeleccionada = this.skills[this.numSkill];

    this.questionService.createQuestion(new Question(this.formData.enunciat.value,
      this.formData.resposta1.value,
      this.formData.resposta2.value,
      this.formData.resposta3.value,
      this.formData.resposta4.value,
      this.respostaCorrecta,
      this.skillSeleccionada.skillId
    ))
      .subscribe(
        () => {
          this.messageService.setMessage({ type: 'success', message: 'Has inserit una pregunta a ' + this.skillSeleccionada.skillName });
          formDirective.resetForm();
          this.questionForm.reset();
          this.submitted = false;
          this.numRespostaCorrecta = 0;
          this.respostaCorrecta = null;
        },
        error => {
          this.messageService.setMessage({ type: 'error', message: error.error.message });
        });
  }

}
