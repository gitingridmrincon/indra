import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IObligacion } from 'app/shared/model/obligacion.model';

type EntityResponseType = HttpResponse<IObligacion>;
type EntityArrayResponseType = HttpResponse<IObligacion[]>;

@Injectable({ providedIn: 'root' })
export class ObligacionService {
  public resourceUrl = SERVER_API_URL + 'api/obligacions';

  constructor(protected http: HttpClient) {}

  create(obligacion: IObligacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(obligacion);
    return this.http
      .post<IObligacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(obligacion: IObligacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(obligacion);
    return this.http
      .put<IObligacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IObligacion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IObligacion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(obligacion: IObligacion): IObligacion {
    const copy: IObligacion = Object.assign({}, obligacion, {
      fecha: obligacion.fecha && obligacion.fecha.isValid() ? obligacion.fecha.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha ? moment(res.body.fecha) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((obligacion: IObligacion) => {
        obligacion.fecha = obligacion.fecha ? moment(obligacion.fecha) : undefined;
      });
    }
    return res;
  }
}
