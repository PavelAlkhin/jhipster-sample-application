import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DocsApplicationJHipsterTestModule } from '../../../test.module';
import { FoldersDetailComponent } from 'app/entities/folders/folders-detail.component';
import { Folders } from 'app/shared/model/folders.model';

describe('Component Tests', () => {
  describe('Folders Management Detail Component', () => {
    let comp: FoldersDetailComponent;
    let fixture: ComponentFixture<FoldersDetailComponent>;
    const route = ({ data: of({ folders: new Folders(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DocsApplicationJHipsterTestModule],
        declarations: [FoldersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FoldersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FoldersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load folders on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.folders).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
