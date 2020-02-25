import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICV } from 'app/shared/model/cv.model';
import { CVService } from './cv.service';
import { CVDeleteDialogComponent } from './cv-delete-dialog.component';

@Component({
  selector: 'jhi-cv',
  templateUrl: './cv.component.html'
})
export class CVComponent implements OnInit, OnDestroy {
  cVS?: ICV[];
  eventSubscriber?: Subscription;

  constructor(protected cVService: CVService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.cVService.query().subscribe((res: HttpResponse<ICV[]>) => {
      this.cVS = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCVS();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICV): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCVS(): void {
    this.eventSubscriber = this.eventManager.subscribe('cVListModification', () => this.loadAll());
  }

  delete(cV: ICV): void {
    const modalRef = this.modalService.open(CVDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cV = cV;
  }
}
