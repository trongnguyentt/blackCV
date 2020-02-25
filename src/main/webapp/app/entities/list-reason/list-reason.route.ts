import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IListReason, ListReason } from 'app/shared/model/list-reason.model';
import { ListReasonService } from './list-reason.service';
import { ListReasonComponent } from './list-reason.component';
import { ListReasonDetailComponent } from './list-reason-detail.component';

@Injectable({ providedIn: 'root' })
export class ListReasonResolve implements Resolve<IListReason> {
  constructor(private service: ListReasonService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IListReason> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((listReason: HttpResponse<ListReason>) => {
          if (listReason.body) {
            return of(listReason.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ListReason());
  }
}

export const listReasonRoute: Routes = [
  {
    path: '',
    component: ListReasonComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'blackcvApp.listReason.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ListReasonDetailComponent,
    resolve: {
      listReason: ListReasonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'blackcvApp.listReason.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
