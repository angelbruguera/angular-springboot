import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../_services/profile.service';

import { Practice } from '../_models/practice';
import { MessageService } from '../_services/message.service';
import { PracticeService } from '../_services/practice.service';
import { LoadingService } from '../_services/loading.service';


@Component({
  selector: 'app-myresults',
  templateUrl: './myresults.component.html',
  styleUrls: ['./myresults.component.css']
})
export class MyResultsComponent implements OnInit {

  practices: Practice[] = [];
  indexPractice: number;
  constructor(
    private profileService: ProfileService,
    private messageService: MessageService,
    private practiceService: PracticeService,
    private loadingService: LoadingService
  ) { }

  ngOnInit() {
    this.loadingService.setLoading({ seconds: 2, information: 'Un moment, siusplau' });
    this.profileService.getPractice().subscribe((data) => {
      this.practices = data;
    });
  }

  /**
   *
   * Aquesta funció s'assigna al event click
   * del botó "Borrar practica" i borra la practica
   * seleccionada
   */
  deletePractice(practice: Practice, id: number) {
    this.indexPractice = id;
    this.practices.splice(this.indexPractice, 1);
    this.practiceService.deletePractice(practice.id).subscribe((data) => {
      this.messageService.setMessage({ type: 'success', message: 'S\'ha eliminat la practica correctament' });
    },
      (error) => {
        this.messageService.setMessage({ type: 'error', message: 'Ha succeït un error' });
      });
  }

}
