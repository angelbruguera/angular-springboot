import { Component, OnInit } from '@angular/core';
import { PracticeService } from '../_services/practice.service';
import { Practice } from '../_models/practice';
import { Skill } from '../_models/skill';
import { SkillService } from '../_services/skill.service';
import { setLeft, setRight } from '../_functions/functions';
import { LoadingService } from '../_services/loading.service';
import { Loading } from '../_models/loading';
import { MessageService } from '../_services/message.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-practice',
  templateUrl: './practice.component.html',
  styleUrls: ['./practice.component.css']
})
export class PracticeComponent implements OnInit {

  practice: Practice;
  skills: Skill[] = [];
  skillSeleccionada: Skill;
  respostaSeleccionada: string;
  loading: Loading;
  idQuestion: number = null;
  numSeleccionat = 0;
  numSkill = 0;
  numQuestion = 0;
  start = false;


  constructor(
    private practiceService: PracticeService,
    private skillService: SkillService,
    private loadingService: LoadingService,
    private messageService: MessageService,
    private router: Router,
  ) { }

  ngOnInit() {
    this.skillService.getAllSkills().subscribe(
      (data) => {
        this.skills = data.filter((skill) => {
          return skill.totalQuestions >= 10;
        });
      }
    );
    this.loadingService.setLoading({ information: 'Un moment, siusplau', seconds: 2 });


  }


  /**
   * Aquesta funció s'assigna al event
   * click del botó "start" i genera
   * la pràctica a partir de la skill
   * seleccionada
   */
  startPractice() {
    this.skillSeleccionada = this.skills[this.numSkill];
    this.practiceService.createPractice(this.skillSeleccionada.skillId).subscribe(
      data => {
        this.practice = data;
        this.loadingService.setLoading({ information: 'A punt per començar, ' + this.practice.candidateUsername + '?', seconds: 5 });
        this.start = true;

      },
      (error) => {
        this.messageService.setMessage({ type: 'error', message: error.error.message });
      }
    )
  }

  /**
   * Aquesta funció s'assigna al event
   * click del botó "seguent" que enviarà
   * i processarà la resposta enviada
   */

  nextQuestion() {
    this.practiceService.postAnswer(this.skillSeleccionada.skillId, this.respostaSeleccionada, this.idQuestion).subscribe(() => {
      if (this.numQuestion === this.practice.questions.length) {
        this.router.navigate(['/me/results']);
        this.messageService.setMessage({ type: 'success', message: 'Practica realitzada' });

      }
    });
    this.respostaSeleccionada = null;
    this.idQuestion = null;
    this.numSeleccionat = 0;
    this.numQuestion += 1;
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
   * Aquesta funció s'assinga al
   * event click del botó de l'esquerra
   * on implementem la funció
   * global setLeft()
   */

  goLeft() {
    this.numSkill = setLeft(this.numSkill, this.skills.length);
  }

  /**
   * Aquesta funció determinarà
   * quina resposta has seleccionat
   */
  setRespostaSeleccionada(resposta: string, numSeleccionat: number, idQuestion) {
    this.respostaSeleccionada = resposta;
    this.numSeleccionat = numSeleccionat;
    this.idQuestion = idQuestion;

  }







}
