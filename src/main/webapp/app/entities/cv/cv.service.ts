import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICV } from 'app/shared/model/cv.model';

type EntityResponseType = HttpResponse<ICV>;
type EntityArrayResponseType = HttpResponse<ICV[]>;

@Injectable({ providedIn: 'root' })
export class CVService {
  public resourceUrl = SERVER_API_URL + 'api/cvs';

  constructor(protected http: HttpClient) {}

  create(cV: ICV): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cV);
    return this.http
      .post<ICV>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cV: ICV): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cV);
    return this.http
      .put<ICV>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICV>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICV[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cV: ICV): ICV {
    const copy: ICV = Object.assign({}, cV, {
      createdDate: cV.createdDate && cV.createdDate.isValid() ? cV.createdDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cV: ICV) => {
        cV.createdDate = cV.createdDate ? moment(cV.createdDate) : undefined;
      });
    }
    return res;
  }
}
