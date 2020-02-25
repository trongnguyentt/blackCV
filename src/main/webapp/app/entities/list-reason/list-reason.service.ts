import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IListReason } from 'app/shared/model/list-reason.model';

type EntityResponseType = HttpResponse<IListReason>;
type EntityArrayResponseType = HttpResponse<IListReason[]>;

@Injectable({ providedIn: 'root' })
export class ListReasonService {
  public resourceUrl = SERVER_API_URL + 'api/list-reasons';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IListReason>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IListReason[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(listReason: IListReason): IListReason {
    const copy: IListReason = Object.assign({}, listReason, {
      createdDate: listReason.createdDate && listReason.createdDate.isValid() ? listReason.createdDate.toJSON() : undefined,
      lastModifiedDate:
        listReason.lastModifiedDate && listReason.lastModifiedDate.isValid() ? listReason.lastModifiedDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? moment(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((listReason: IListReason) => {
        listReason.createdDate = listReason.createdDate ? moment(listReason.createdDate) : undefined;
        listReason.lastModifiedDate = listReason.lastModifiedDate ? moment(listReason.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
