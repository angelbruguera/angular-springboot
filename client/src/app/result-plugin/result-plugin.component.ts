import { Component, OnInit, Input } from '@angular/core';
import { Practice } from '../_models/practice';

@Component({
  selector: 'app-result-plugin',
  templateUrl: './result-plugin.component.html',
  styleUrls: ['./result-plugin.component.css']
})
export class ResultPluginComponent implements OnInit {

  @Input() practice: Practice;

  constructor() { }

  ngOnInit() {
  }

}
