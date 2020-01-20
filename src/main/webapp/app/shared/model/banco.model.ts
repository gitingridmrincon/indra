export interface IBanco {
  id?: number;
  codigoBanco?: string;
  nombre?: string;
}

export class Banco implements IBanco {
  constructor(public id?: number, public codigoBanco?: string, public nombre?: string) {}
}
