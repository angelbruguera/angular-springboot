import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { MessageComponent } from './message/message.component';
import { QuestionComponent } from './question/question.component';
import { JwtInterceptor } from './_helpers/jwt.interceptor';
import { PracticeComponent } from './practice/practice.component';
import { MyQuestionsComponent } from './myquestions/myquestions.component';
import { MyResultsComponent } from './myresults/myresults.component';
import { PluginQuestionComponent } from './plugin-question/plugin-question.component';
import { PersonComponent } from './person/person.component';
import { FindResultsComponent } from './find-results/find-results.component';
import { StaticSiteComponent } from './static-site/static-site.component';
import { LoadingComponent } from './loading/loading.component';
import { ResultPluginComponent } from './result-plugin/result-plugin.component';
import { PolicyComponent } from './policy/policy.component';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    MessageComponent,
    QuestionComponent,
    PracticeComponent,
    PluginQuestionComponent,
    PersonComponent,
    FindResultsComponent,
    MyQuestionsComponent,
    MyResultsComponent,
    StaticSiteComponent,
    LoadingComponent,
    ResultPluginComponent,
    PolicyComponent,


  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
