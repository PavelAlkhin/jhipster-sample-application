import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDocuments } from 'app/shared/model/documents.model';

type EntityResponseType = HttpResponse<IDocuments>;
type EntityArrayResponseType = HttpResponse<IDocuments[]>;

@Injectable({ providedIn: 'root' })
export class DocumentsService {
  public resourceUrl = SERVER_API_URL + 'api/documents';

  constructor(protected http: HttpClient) {}

  create(documents: IDocuments): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documents);
    return this.http
      .post<IDocuments>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(documents: IDocuments): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documents);
    return this.http
      .put<IDocuments>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDocuments>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDocuments[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(documents: IDocuments): IDocuments {
    const copy: IDocuments = Object.assign({}, documents, {
      date: documents.date && documents.date.isValid() ? documents.date.toJSON() : undefined,
      folderguid: documents.folderguid && documents.folderguid.isValid() ? documents.folderguid.toJSON() : undefined,
      docdate: documents.docdate && documents.docdate.isValid() ? documents.docdate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
      res.body.folderguid = res.body.folderguid ? moment(res.body.folderguid) : undefined;
      res.body.docdate = res.body.docdate ? moment(res.body.docdate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((documents: IDocuments) => {
        documents.date = documents.date ? moment(documents.date) : undefined;
        documents.folderguid = documents.folderguid ? moment(documents.folderguid) : undefined;
        documents.docdate = documents.docdate ? moment(documents.docdate) : undefined;
      });
    }
    return res;
  }
}
