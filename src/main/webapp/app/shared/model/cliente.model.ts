import { TipoDeDocumento } from 'app/shared/model/enumerations/tipo-de-documento.model';

export interface ICliente {
  id?: number;
  identificacion?: string;
  tipoDeDocumento?: TipoDeDocumento;
  nombreDelCliente?: string;
  segundoNombreDelCliente?: string;
  primerApellido?: string;
  segundoApellido?: string;
  rezonSocial?: string;
  direccion?: string;
  telefono?: string;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public identificacion?: string,
    public tipoDeDocumento?: TipoDeDocumento,
    public nombreDelCliente?: string,
    public segundoNombreDelCliente?: string,
    public primerApellido?: string,
    public segundoApellido?: string,
    public rezonSocial?: string,
    public direccion?: string,
    public telefono?: string
  ) {}
}
