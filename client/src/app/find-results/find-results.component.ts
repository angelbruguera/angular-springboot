import { Component, OnInit } from '@angular/core';
import { Skill } from '../_models/skill';
import { Practice } from '../_models/practice';
import { SkillService } from '../_services/skill.service';
import { setLeft, setRight } from '../_functions/functions';
import { PracticeService } from '../_services/practice.service';
import { LoadingService } from '../_services/loading.service';

@Component({
  selector: 'app-find-results',
  templateUrl: './find-results.component.html',
  styleUrls: ['./find-results.component.css']
})
export class FindResultsComponent implements OnInit {

  skills: Skill[] = [];
  skillSeleccionada: Skill;
  countSkill = 0;
  practices: Practice[] = [];

  constructor(
    private skillService: SkillService,
    private practiceService: PracticeService,
    private loadingService: LoadingService
  ) { }

  ngOnInit() {
    this.loadingService.setLoading({ seconds: 2, information: 'Un moment, siusplau' });
    this.skillService.getAllSkills().subscribe(async (data) => {
      this.skills = await data;
      this.practiceService.getPracticesBySkill(this.skills[this.countSkill].skillId).subscribe((data) => {
        this.practices = data;
      });
    });

  }

  /**
   * Aquesta funció s'assinga al
   * event click del botó de l'esquerra
   * on implementem la funció
   * global setLeft()
   * 
   * Al inicialitzar la funció
   * busquem les practiques relacionades
   * amb la nova skill seleccionada
   */
  goLeft() {
    this.practices = [];
    this.countSkill = setLeft(this.countSkill, this.skills.length);
    this.practiceService.getPracticesBySkill(this.skills[this.countSkill].skillId).subscribe((data) => { this.practices = data; });
  }

  /**
   * Aquesta funció s'assinga al
   * event click del botó de la dreta
   * on implementem la funció
   * global setRight() 
   *
   * Al inicialitzar la funció
   * busquem les practiques relacionades
   * amb la nova skill seleccionada
   */
  goRight() {
    this.practices = [];
    this.countSkill = setRight(this.countSkill, this.skills.length);
    this.practiceService.getPracticesBySkill(this.skills[this.countSkill].skillId).subscribe((data) => { this.practices = data; });
  }


}
