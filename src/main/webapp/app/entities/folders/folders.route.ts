import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFolders, Folders } from 'app/shared/model/folders.model';
import { FoldersService } from './folders.service';
import { FoldersComponent } from './folders.component';
import { FoldersDetailComponent } from './folders-detail.component';
import { FoldersUpdateComponent } from './folders-update.component';

@Injectable({ providedIn: 'root' })
export class FoldersResolve implements Resolve<IFolders> {
  constructor(private service: FoldersService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFolders> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((folders: HttpResponse<Folders>) => {
          if (folders.body) {
            return of(folders.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Folders());
  }
}

export const foldersRoute: Routes = [
  {
    path: '',
    component: FoldersComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'docsApplicationJHipsterApp.folders.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FoldersDetailComponent,
    resolve: {
      folders: FoldersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'docsApplicationJHipsterApp.folders.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FoldersUpdateComponent,
    resolve: {
      folders: FoldersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'docsApplicationJHipsterApp.folders.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FoldersUpdateComponent,
    resolve: {
      folders: FoldersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'docsApplicationJHipsterApp.folders.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
