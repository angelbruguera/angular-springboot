import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-static-site',
  templateUrl: './static-site.component.html',
  styleUrls: ['./static-site.component.css']
})
export class StaticSiteComponent implements OnInit {

  candidate = true;
  constructor() { }

  ngOnInit() {
  }

  /**
   * Aquesta funció
   * defineix quin rol volem
   * veure
   */
  setRole() {
    return this.candidate = !this.candidate;
  }

}
