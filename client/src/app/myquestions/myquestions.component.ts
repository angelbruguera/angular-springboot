import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../_services/profile.service';
import { Question } from '../_models/question';
import { QuestionService } from '../_services/question.service';
import { MessageService } from '../_services/message.service';
import { LoadingService } from '../_services/loading.service';

@Component({
  selector: 'app-myquestions',
  templateUrl: './myquestions.component.html',
  styleUrls: ['./myquestions.component.css']
})
export class MyQuestionsComponent implements OnInit {

  questions: Question[] = [];
  indexQuestion: number;
  constructor(
    private profileService: ProfileService,
    private questionService: QuestionService,
    private messageService: MessageService,
    private loadingService: LoadingService
  ) { }

  ngOnInit() {
    this.loadingService.setLoading({ seconds: 2, information: 'Un moment, siusplau' });
    this.profileService.getQuestions().subscribe((data) => { this.questions = data; });
  }

  /**
   * 
   * Aquesta funció s'assigna al event click
   * del botó "Borrar pregunta" i borra la pregunta
   * seleccionada
   */
  deleteQuestion(id: number, index: number) {
    this.indexQuestion = index;
    this.questions.splice(this.indexQuestion, 1);
    this.indexQuestion = null;
    this.questionService.deleteQuestion(id).subscribe(() => {
      this.messageService.setMessage({ type: 'success', message: 'S\'ha eliminat la pregunta correctament' });
    },
      (error) => {
        this.messageService.setMessage({ type: 'error', message: 'Ha succeït un error' });
      });
  }

}
