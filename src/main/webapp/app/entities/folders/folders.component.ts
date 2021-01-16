import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFolders } from 'app/shared/model/folders.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FoldersService } from './folders.service';
import { FoldersDeleteDialogComponent } from './folders-delete-dialog.component';

@Component({
  selector: 'jhi-folders',
  templateUrl: './folders.component.html',
})
export class FoldersComponent implements OnInit, OnDestroy {
  folders: IFolders[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected foldersService: FoldersService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.folders = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.foldersService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IFolders[]>) => this.paginateFolders(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.folders = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFolders();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFolders): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFolders(): void {
    this.eventSubscriber = this.eventManager.subscribe('foldersListModification', () => this.reset());
  }

  delete(folders: IFolders): void {
    const modalRef = this.modalService.open(FoldersDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.folders = folders;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateFolders(data: IFolders[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.folders.push(data[i]);
      }
    }
  }
}
