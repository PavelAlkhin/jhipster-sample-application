import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFolders } from 'app/shared/model/folders.model';
import { FoldersService } from './folders.service';

@Component({
  templateUrl: './folders-delete-dialog.component.html',
})
export class FoldersDeleteDialogComponent {
  folders?: IFolders;

  constructor(protected foldersService: FoldersService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.foldersService.delete(id).subscribe(() => {
      this.eventManager.broadcast('foldersListModification');
      this.activeModal.close();
    });
  }
}
