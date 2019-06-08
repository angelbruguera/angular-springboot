import { NgModule } from '@angular/core';
import { MatProgressSpinnerModule, MatBadgeModule, MatChipsModule, MatIconModule, MatInputModule, MatButtonModule, MatCheckboxModule, MatFormFieldModule } from '@angular/material';

@NgModule({
  imports: [
    MatButtonModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatChipsModule,
    MatBadgeModule,
    MatProgressSpinnerModule
  ],
  exports: [
    MatButtonModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatChipsModule,
    MatBadgeModule,
    MatProgressSpinnerModule
  ]
})
export class MaterialModule { }
