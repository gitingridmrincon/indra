import { Moment } from 'moment';
import { IObligacion } from 'app/shared/model/obligacion.model';
import { ICliente } from 'app/shared/model/cliente.model';
import { IBanco } from 'app/shared/model/banco.model';

export interface IPago {
  id?: number;
  codigoPago?: string;
  valorPagado?: number;
  fechaPago?: Moment;
  periodoPagado?: number;
  obligacion?: IObligacion;
  cliente?: ICliente;
  banco?: IBanco;
}

export class Pago implements IPago {
  constructor(
    public id?: number,
    public codigoPago?: string,
    public valorPagado?: number,
    public fechaPago?: Moment,
    public periodoPagado?: number,
    public obligacion?: IObligacion,
    public cliente?: ICliente,
    public banco?: IBanco
  ) {}
}
