import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'folders',
        loadChildren: () => import('./folders/folders.module').then(m => m.DocsApplicationJHipsterFoldersModule),
      },
      {
        path: 'documents',
        loadChildren: () => import('./documents/documents.module').then(m => m.DocsApplicationJHipsterDocumentsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class DocsApplicationJHipsterEntityModule {}
