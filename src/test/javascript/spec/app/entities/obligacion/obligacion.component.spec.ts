import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ObligacionesTestModule } from '../../../test.module';
import { ObligacionComponent } from 'app/entities/obligacion/obligacion.component';
import { ObligacionService } from 'app/entities/obligacion/obligacion.service';
import { Obligacion } from 'app/shared/model/obligacion.model';

describe('Component Tests', () => {
  describe('Obligacion Management Component', () => {
    let comp: ObligacionComponent;
    let fixture: ComponentFixture<ObligacionComponent>;
    let service: ObligacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ObligacionesTestModule],
        declarations: [ObligacionComponent],
        providers: []
      })
        .overrideTemplate(ObligacionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ObligacionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ObligacionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Obligacion(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.obligacions && comp.obligacions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
