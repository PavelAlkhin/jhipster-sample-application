import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DocsApplicationJHipsterSharedModule } from 'app/shared/shared.module';
import { FoldersComponent } from './folders.component';
import { FoldersDetailComponent } from './folders-detail.component';
import { FoldersUpdateComponent } from './folders-update.component';
import { FoldersDeleteDialogComponent } from './folders-delete-dialog.component';
import { foldersRoute } from './folders.route';

@NgModule({
  imports: [DocsApplicationJHipsterSharedModule, RouterModule.forChild(foldersRoute)],
  declarations: [FoldersComponent, FoldersDetailComponent, FoldersUpdateComponent, FoldersDeleteDialogComponent],
  entryComponents: [FoldersDeleteDialogComponent],
})
export class DocsApplicationJHipsterFoldersModule {}
