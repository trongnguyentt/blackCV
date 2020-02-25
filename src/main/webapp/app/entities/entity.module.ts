import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cv',
        loadChildren: () => import('./cv/cv.module').then(m => m.BlackcvCVModule)
      },
      {
        path: 'company',
        loadChildren: () => import('./company/company.module').then(m => m.BlackcvCompanyModule)
      },
      {
        path: 'list-reason',
        loadChildren: () => import('./list-reason/list-reason.module').then(m => m.BlackcvListReasonModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class BlackcvEntityModule {}
