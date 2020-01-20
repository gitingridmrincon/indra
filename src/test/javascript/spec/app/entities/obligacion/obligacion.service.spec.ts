import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ObligacionService } from 'app/entities/obligacion/obligacion.service';
import { IObligacion, Obligacion } from 'app/shared/model/obligacion.model';
import { Estado } from 'app/shared/model/enumerations/estado.model';

describe('Service Tests', () => {
  describe('Obligacion Service', () => {
    let injector: TestBed;
    let service: ObligacionService;
    let httpMock: HttpTestingController;
    let elemDefault: IObligacion;
    let expectedResult: IObligacion | IObligacion[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ObligacionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Obligacion(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 0, 0, 0, 0, false, Estado.PAGADO);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Obligacion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fecha: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fecha: currentDate
          },
          returnedFromService
        );
        service
          .create(new Obligacion())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Obligacion', () => {
        const returnedFromService = Object.assign(
          {
            codigoObligacion: 'BBBBBB',
            nombre: 'BBBBBB',
            descripcion: 'BBBBBB',
            fecha: currentDate.format(DATE_FORMAT),
            valorTotal: 1,
            valorPeriodo: 1,
            nomeroPeriodos: 1,
            periodoActual: 1,
            vigente: true,
            estado: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Obligacion', () => {
        const returnedFromService = Object.assign(
          {
            codigoObligacion: 'BBBBBB',
            nombre: 'BBBBBB',
            descripcion: 'BBBBBB',
            fecha: currentDate.format(DATE_FORMAT),
            valorTotal: 1,
            valorPeriodo: 1,
            nomeroPeriodos: 1,
            periodoActual: 1,
            vigente: true,
            estado: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fecha: currentDate
          },
          returnedFromService
        );
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Obligacion', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
