import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFolders, Folders } from 'app/shared/model/folders.model';
import { FoldersService } from './folders.service';

@Component({
  selector: 'jhi-folders-update',
  templateUrl: './folders-update.component.html',
})
export class FoldersUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    folder: [],
  });

  constructor(protected foldersService: FoldersService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ folders }) => {
      this.updateForm(folders);
    });
  }

  updateForm(folders: IFolders): void {
    this.editForm.patchValue({
      id: folders.id,
      folder: folders.folder,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const folders = this.createFromForm();
    if (folders.id !== undefined) {
      this.subscribeToSaveResponse(this.foldersService.update(folders));
    } else {
      this.subscribeToSaveResponse(this.foldersService.create(folders));
    }
  }

  private createFromForm(): IFolders {
    return {
      ...new Folders(),
      id: this.editForm.get(['id'])!.value,
      folder: this.editForm.get(['folder'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFolders>>): void {
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
