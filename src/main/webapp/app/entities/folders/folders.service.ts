import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFolders } from 'app/shared/model/folders.model';

type EntityResponseType = HttpResponse<IFolders>;
type EntityArrayResponseType = HttpResponse<IFolders[]>;

@Injectable({ providedIn: 'root' })
export class FoldersService {
  public resourceUrl = SERVER_API_URL + 'api/folders';

  constructor(protected http: HttpClient) {}

  create(folders: IFolders): Observable<EntityResponseType> {
    return this.http.post<IFolders>(this.resourceUrl, folders, { observe: 'response' });
  }

  update(folders: IFolders): Observable<EntityResponseType> {
    return this.http.put<IFolders>(this.resourceUrl, folders, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFolders>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFolders[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
