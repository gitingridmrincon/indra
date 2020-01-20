import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPago } from 'app/shared/model/pago.model';

type EntityResponseType = HttpResponse<IPago>;
type EntityArrayResponseType = HttpResponse<IPago[]>;

@Injectable({ providedIn: 'root' })
export class PagoService {
  public resourceUrl = SERVER_API_URL + 'api/pagos';

  constructor(protected http: HttpClient) {}

  create(pago: IPago): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pago);
    return this.http
      .post<IPago>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pago: IPago): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pago);
    return this.http
      .put<IPago>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPago>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPago[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pago: IPago): IPago {
    const copy: IPago = Object.assign({}, pago, {
      fechaPago: pago.fechaPago && pago.fechaPago.isValid() ? pago.fechaPago.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaPago = res.body.fechaPago ? moment(res.body.fechaPago) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pago: IPago) => {
        pago.fechaPago = pago.fechaPago ? moment(pago.fechaPago) : undefined;
      });
    }
    return res;
  }
}
