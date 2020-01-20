import { Moment } from 'moment';
import { IProducto } from 'app/shared/model/producto.model';
import { Estado } from 'app/shared/model/enumerations/estado.model';

export interface IObligacion {
  id?: number;
  codigoObligacion?: string;
  nombre?: string;
  descripcion?: string;
  fecha?: Moment;
  valorTotal?: number;
  valorPeriodo?: number;
  nomeroPeriodos?: number;
  periodoActual?: number;
  vigente?: boolean;
  estado?: Estado;
  producto?: IProducto;
}

export class Obligacion implements IObligacion {
  constructor(
    public id?: number,
    public codigoObligacion?: string,
    public nombre?: string,
    public descripcion?: string,
    public fecha?: Moment,
    public valorTotal?: number,
    public valorPeriodo?: number,
    public nomeroPeriodos?: number,
    public periodoActual?: number,
    public vigente?: boolean,
    public estado?: Estado,
    public producto?: IProducto
  ) {
    this.vigente = this.vigente || false;
  }
}
