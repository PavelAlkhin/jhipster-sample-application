import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDocuments, Documents } from 'app/shared/model/documents.model';
import { DocumentsService } from './documents.service';
import { IFolders } from 'app/shared/model/folders.model';
import { FoldersService } from 'app/entities/folders/folders.service';

@Component({
  selector: 'jhi-documents-update',
  templateUrl: './documents-update.component.html',
})
export class DocumentsUpdateComponent implements OnInit {
  isSaving = false;
  folders: IFolders[] = [];

  editForm = this.fb.group({
    id: [],
    doctype: [],
    filename: [],
    date: [],
    folderguid: [],
    folder: [],
    docnumber: [],
    docdate: [],
    description: [],
    datetime: [],
    typedoc: [],
    folderid: [],
  });

  constructor(
    protected documentsService: DocumentsService,
    protected foldersService: FoldersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documents }) => {
      if (!documents.id) {
        const today = moment().startOf('day');
        documents.date = today;
        documents.folderguid = today;
        documents.docdate = today;
      }

      this.updateForm(documents);

      this.foldersService.query().subscribe((res: HttpResponse<IFolders[]>) => (this.folders = res.body || []));
    });
  }

  updateForm(documents: IDocuments): void {
    this.editForm.patchValue({
      id: documents.id,
      doctype: documents.doctype,
      filename: documents.filename,
      date: documents.date ? documents.date.format(DATE_TIME_FORMAT) : null,
      folderguid: documents.folderguid ? documents.folderguid.format(DATE_TIME_FORMAT) : null,
      folder: documents.folder,
      docnumber: documents.docnumber,
      docdate: documents.docdate ? documents.docdate.format(DATE_TIME_FORMAT) : null,
      description: documents.description,
      datetime: documents.datetime,
      typedoc: documents.typedoc,
      folderid: documents.folderid,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documents = this.createFromForm();
    if (documents.id !== undefined) {
      this.subscribeToSaveResponse(this.documentsService.update(documents));
    } else {
      this.subscribeToSaveResponse(this.documentsService.create(documents));
    }
  }

  private createFromForm(): IDocuments {
    return {
      ...new Documents(),
      id: this.editForm.get(['id'])!.value,
      doctype: this.editForm.get(['doctype'])!.value,
      filename: this.editForm.get(['filename'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      folderguid: this.editForm.get(['folderguid'])!.value ? moment(this.editForm.get(['folderguid'])!.value, DATE_TIME_FORMAT) : undefined,
      folder: this.editForm.get(['folder'])!.value,
      docnumber: this.editForm.get(['docnumber'])!.value,
      docdate: this.editForm.get(['docdate'])!.value ? moment(this.editForm.get(['docdate'])!.value, DATE_TIME_FORMAT) : undefined,
      description: this.editForm.get(['description'])!.value,
      datetime: this.editForm.get(['datetime'])!.value,
      typedoc: this.editForm.get(['typedoc'])!.value,
      folderid: this.editForm.get(['folderid'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocuments>>): void {
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

  trackById(index: number, item: IFolders): any {
    return item.id;
  }
}
