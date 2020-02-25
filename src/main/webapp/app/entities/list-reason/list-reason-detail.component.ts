import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IListReason } from 'app/shared/model/list-reason.model';

@Component({
  selector: 'jhi-list-reason-detail',
  templateUrl: './list-reason-detail.component.html'
})
export class ListReasonDetailComponent implements OnInit {
  listReason: IListReason | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ listReason }) => {
      this.listReason = listReason;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
