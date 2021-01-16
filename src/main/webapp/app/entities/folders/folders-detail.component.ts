import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFolders } from 'app/shared/model/folders.model';

@Component({
  selector: 'jhi-folders-detail',
  templateUrl: './folders-detail.component.html',
})
export class FoldersDetailComponent implements OnInit {
  folders: IFolders | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ folders }) => (this.folders = folders));
  }

  previousState(): void {
    window.history.back();
  }
}
