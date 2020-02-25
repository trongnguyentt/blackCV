import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IListReason } from 'app/shared/model/list-reason.model';
import { ListReasonService } from './list-reason.service';

@Component({
  selector: 'jhi-list-reason',
  templateUrl: './list-reason.component.html'
})
export class ListReasonComponent implements OnInit, OnDestroy {
  listReasons?: IListReason[];
  eventSubscriber?: Subscription;

  constructor(protected listReasonService: ListReasonService, protected eventManager: JhiEventManager) {}

  loadAll(): void {
    this.listReasonService.query().subscribe((res: HttpResponse<IListReason[]>) => {
      this.listReasons = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInListReasons();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IListReason): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInListReasons(): void {
    this.eventSubscriber = this.eventManager.subscribe('listReasonListModification', () => this.loadAll());
  }
}
