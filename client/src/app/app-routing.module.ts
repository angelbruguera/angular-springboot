import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { AuthGuard } from './_guards/auth.guard';
import { Role } from './_models/role';
import { QuestionComponent } from './question/question.component';
import { PracticeComponent } from './practice/practice.component';
import { MyQuestionsComponent } from './myquestions/myquestions.component';
import { MyResultsComponent } from './myresults/myresults.component';
import { FindResultsComponent } from './find-results/find-results.component';
import { PolicyComponent } from './policy/policy.component';
import { StaticSiteComponent } from './static-site/static-site.component';

const routes: Routes = [

  {
    path: '',
    component: StaticSiteComponent,
  },
  {
    path: 'signin',
    component: LoginComponent,
  },
  {
    path: 'signup',
    component: SignupComponent,
  },
  {
    path: 'policy',
    component: PolicyComponent,
  },
  {
    path: 'me/questions',
    component: MyQuestionsComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.BUSINESS] }
  },
  {
    path: 'me/results',
    component: MyResultsComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.CANDIDATE] }
  },
  {
    path: 'question',
    component: QuestionComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.BUSINESS] }
  },
  {
    path: 'practice',
    component: PracticeComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.CANDIDATE] }
  },
  {
    path: 'results',
    component: FindResultsComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.BUSINESS] }
  },

  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
