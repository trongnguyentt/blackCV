import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlackcvSharedModule } from 'app/shared/shared.module';
import { ListReasonComponent } from './list-reason.component';
import { ListReasonDetailComponent } from './list-reason-detail.component';
import { listReasonRoute } from './list-reason.route';

@NgModule({
  imports: [BlackcvSharedModule, RouterModule.forChild(listReasonRoute)],
  declarations: [ListReasonComponent, ListReasonDetailComponent],
  entryComponents: []
})
export class BlackcvListReasonModule {}
