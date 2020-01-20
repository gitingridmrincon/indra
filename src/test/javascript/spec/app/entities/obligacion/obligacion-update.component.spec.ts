import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ObligacionesTestModule } from '../../../test.module';
import { ObligacionUpdateComponent } from 'app/entities/obligacion/obligacion-update.component';
import { ObligacionService } from 'app/entities/obligacion/obligacion.service';
import { Obligacion } from 'app/shared/model/obligacion.model';

describe('Component Tests', () => {
  describe('Obligacion Management Update Component', () => {
    let comp: ObligacionUpdateComponent;
    let fixture: ComponentFixture<ObligacionUpdateComponent>;
    let service: ObligacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ObligacionesTestModule],
        declarations: [ObligacionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ObligacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ObligacionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ObligacionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Obligacion(123);
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
        const entity = new Obligacion();
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
