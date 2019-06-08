import { Component, OnInit, OnDestroy } from '@angular/core';
import { Loading } from '../_models/loading';
import { LoadingService } from '../_services/loading.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.css']
})
export class LoadingComponent implements OnInit, OnDestroy {

  loading: Loading;
  private loadingSubscription: Subscription;
  constructor(
    private loadingService: LoadingService
  ) { }

  ngOnInit() {
    this.loadingSubscription = this.loadingService.currentLoading.subscribe(async (data) => {
      this.loading = await data;

    });

  }

  ngOnDestroy() {
    this.loadingSubscription.unsubscribe();
  }

}
