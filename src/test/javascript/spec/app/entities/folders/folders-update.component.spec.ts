import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DocsApplicationJHipsterTestModule } from '../../../test.module';
import { FoldersUpdateComponent } from 'app/entities/folders/folders-update.component';
import { FoldersService } from 'app/entities/folders/folders.service';
import { Folders } from 'app/shared/model/folders.model';

describe('Component Tests', () => {
  describe('Folders Management Update Component', () => {
    let comp: FoldersUpdateComponent;
    let fixture: ComponentFixture<FoldersUpdateComponent>;
    let service: FoldersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DocsApplicationJHipsterTestModule],
        declarations: [FoldersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FoldersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FoldersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FoldersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Folders(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Folders();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
