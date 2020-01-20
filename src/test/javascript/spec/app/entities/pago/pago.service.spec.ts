import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PagoService } from 'app/entities/pago/pago.service';
import { IPago, Pago } from 'app/shared/model/pago.model';

describe('Service Tests', () => {
  describe('Pago Service', () => {
    let injector: TestBed;
    let service: PagoService;
    let httpMock: HttpTestingController;
    let elemDefault: IPago;
    let expectedResult: IPago | IPago[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PagoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Pago(0, 'AAAAAAA', 0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaPago: currentDate.format(DATE_FORMAT)
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

      it('should create a Pago', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaPago: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaPago: currentDate
          },
          returnedFromService
        );
        service
          .create(new Pago())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Pago', () => {
        const returnedFromService = Object.assign(
          {
            codigoPago: 'BBBBBB',
            valorPagado: 1,
            fechaPago: currentDate.format(DATE_FORMAT),
            periodoPagado: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaPago: currentDate
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

      it('should return a list of Pago', () => {
        const returnedFromService = Object.assign(
          {
            codigoPago: 'BBBBBB',
            valorPagado: 1,
            fechaPago: currentDate.format(DATE_FORMAT),
            periodoPagado: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaPago: currentDate
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

      it('should delete a Pago', () => {
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
