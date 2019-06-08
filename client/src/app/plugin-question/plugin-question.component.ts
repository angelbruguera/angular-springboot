import { Component, OnInit, Input } from '@angular/core';
import { Question } from 'src/app/_models/question';
import { setLeft, setRight } from 'src/app/_functions/functions';
import { Answer } from 'src/app/_models/answer';

@Component({
  selector: 'app-plugin-question',
  templateUrl: './plugin-question.component.html',
  styleUrls: ['./plugin-question.component.css'],
})
export class PluginQuestionComponent implements OnInit {
  @Input() questions: Question[];
  @Input() answers: Answer[];
  indexQuestion = 0;
  constructor() { }

  ngOnInit() {
  }

  /**
   * Aquesta funció s'assinga al
   * event click del botó de l'esquerra
   * on implementem la funció
   * global setLeft()
   */
  goLeft() {
    this.indexQuestion = setLeft(this.indexQuestion, this.questions.length);
  }

  /**
   * Aquesta funció s'assinga al
   * event click del botó de la dreta
   * on implementem la funció
   * global setRight()
   */
  goRight() {
    this.indexQuestion = setRight(this.indexQuestion, this.questions.length);
  }

}
