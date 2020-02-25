import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICV, CV } from 'app/shared/model/cv.model';
import { CVService } from './cv.service';

@Component({
  selector: 'jhi-cv-update',
  templateUrl: './cv-update.component.html'
})
export class CVUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    iD: [],
    name: [],
    phone: [],
    email: [],
    address: [],
    job: [],
    avatar: [],
    fileUploadCV: [],
    createdBy: [],
    createdDate: [],
    lastModifiedBy: []
  });

  constructor(protected cVService: CVService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cV }) => {
      this.updateForm(cV);
    });
  }

  updateForm(cV: ICV): void {
    this.editForm.patchValue({
      id: cV.id,
      iD: cV.iD,
      name: cV.name,
      phone: cV.phone,
      email: cV.email,
      address: cV.address,
      job: cV.job,
      avatar: cV.avatar,
      fileUploadCV: cV.fileUploadCV,
      createdBy: cV.createdBy,
      createdDate: cV.createdDate != null ? cV.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: cV.lastModifiedBy
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cV = this.createFromForm();
    if (cV.id !== undefined) {
      this.subscribeToSaveResponse(this.cVService.update(cV));
    } else {
      this.subscribeToSaveResponse(this.cVService.create(cV));
    }
  }

  private createFromForm(): ICV {
    return {
      ...new CV(),
      id: this.editForm.get(['id'])!.value,
      iD: this.editForm.get(['iD'])!.value,
      name: this.editForm.get(['name'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      email: this.editForm.get(['email'])!.value,
      address: this.editForm.get(['address'])!.value,
      job: this.editForm.get(['job'])!.value,
      avatar: this.editForm.get(['avatar'])!.value,
      fileUploadCV: this.editForm.get(['fileUploadCV'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate:
        this.editForm.get(['createdDate'])!.value != null ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT) : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICV>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
