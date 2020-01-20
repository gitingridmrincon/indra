import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ObligacionesTestModule } from '../../../test.module';
import { PagoComponent } from 'app/entities/pago/pago.component';
import { PagoService } from 'app/entities/pago/pago.service';
import { Pago } from 'app/shared/model/pago.model';

describe('Component Tests', () => {
  describe('Pago Management Component', () => {
    let comp: PagoComponent;
    let fixture: ComponentFixture<PagoComponent>;
    let service: PagoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ObligacionesTestModule],
        declarations: [PagoComponent],
        providers: []
      })
        .overrideTemplate(PagoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PagoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PagoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Pago(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pagos && comp.pagos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
