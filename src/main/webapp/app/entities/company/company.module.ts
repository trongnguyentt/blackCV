import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlackcvSharedModule } from 'app/shared/shared.module';
import { CompanyComponent } from './company.component';
import { CompanyDetailComponent } from './company-detail.component';
import { companyRoute } from './company.route';

@NgModule({
  imports: [BlackcvSharedModule, RouterModule.forChild(companyRoute)],
  declarations: [CompanyComponent, CompanyDetailComponent],
  entryComponents: []
})
export class BlackcvCompanyModule {}
