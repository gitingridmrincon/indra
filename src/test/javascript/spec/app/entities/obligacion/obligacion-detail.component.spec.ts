import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ObligacionesTestModule } from '../../../test.module';
import { ObligacionDetailComponent } from 'app/entities/obligacion/obligacion-detail.component';
import { Obligacion } from 'app/shared/model/obligacion.model';

describe('Component Tests', () => {
  describe('Obligacion Management Detail Component', () => {
    let comp: ObligacionDetailComponent;
    let fixture: ComponentFixture<ObligacionDetailComponent>;
    const route = ({ data: of({ obligacion: new Obligacion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ObligacionesTestModule],
        declarations: [ObligacionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ObligacionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ObligacionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load obligacion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.obligacion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
