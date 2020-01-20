export interface IProducto {
  id?: number;
  codigoProducto?: string;
  nombre?: string;
}

export class Producto implements IProducto {
  constructor(public id?: number, public codigoProducto?: string, public nombre?: string) {}
}
