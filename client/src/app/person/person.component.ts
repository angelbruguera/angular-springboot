import { Component, OnInit } from '@angular/core';
import { Profile } from '../_models/profile';
import { ProfileService } from '../_services/profile.service';
import { AuthenticationService } from '../_services/authentication.service';

@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrls: ['./person.component.css']
})
export class PersonComponent implements OnInit {
  perfil: Profile;
  isCandidate: boolean;
  href: string;
  constructor(
    private profileService: ProfileService,
    private authenticationService: AuthenticationService,
  ) { }

  ngOnInit() {
    this.profileService.getProfile().subscribe((data) => { this.perfil = data; });
    this.href = window.location.pathname;
    this.isCandidate = this.authenticationService.isCandidate();
  }

}
