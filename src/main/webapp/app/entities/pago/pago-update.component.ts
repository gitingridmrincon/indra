import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { IPago, Pago } from 'app/shared/model/pago.model';
import { PagoService } from './pago.service';
import { IObligacion } from 'app/shared/model/obligacion.model';
import { ObligacionService } from 'app/entities/obligacion/obligacion.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';
import { IBanco } from 'app/shared/model/banco.model';
import { BancoService } from 'app/entities/banco/banco.service';

type SelectableEntity = IObligacion | ICliente | IBanco;

@Component({
  selector: 'jhi-pago-update',
  templateUrl: './pago-update.component.html'
})
export class PagoUpdateComponent implements OnInit {
  isSaving = false;

  obligacions: IObligacion[] = [];

  clientes: ICliente[] = [];

  bancos: IBanco[] = [];
  fechaPagoDp: any;

  editForm = this.fb.group({
    id: [],
    codigoPago: [],
    valorPagado: [],
    fechaPago: [],
    periodoPagado: [],
    obligacion: [],
    cliente: [],
    banco: []
  });

  constructor(
    protected pagoService: PagoService,
    protected obligacionService: ObligacionService,
    protected clienteService: ClienteService,
    protected bancoService: BancoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pago }) => {
      this.updateForm(pago);

      this.obligacionService
        .query()
        .pipe(
          map((res: HttpResponse<IObligacion[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IObligacion[]) => (this.obligacions = resBody));

      this.clienteService
        .query()
        .pipe(
          map((res: HttpResponse<ICliente[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICliente[]) => (this.clientes = resBody));

      this.bancoService
        .query()
        .pipe(
          map((res: HttpResponse<IBanco[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IBanco[]) => (this.bancos = resBody));
    });
  }

  updateForm(pago: IPago): void {
    this.editForm.patchValue({
      id: pago.id,
      codigoPago: pago.codigoPago,
      valorPagado: pago.valorPagado,
      fechaPago: pago.fechaPago,
      periodoPagado: pago.periodoPagado,
      obligacion: pago.obligacion,
      cliente: pago.cliente,
      banco: pago.banco
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pago = this.createFromForm();
    if (pago.id !== undefined) {
      this.subscribeToSaveResponse(this.pagoService.update(pago));
    } else {
      this.subscribeToSaveResponse(this.pagoService.create(pago));
    }
  }

  private createFromForm(): IPago {
    return {
      ...new Pago(),
      id: this.editForm.get(['id'])!.value,
      codigoPago: this.editForm.get(['codigoPago'])!.value,
      valorPagado: this.editForm.get(['valorPagado'])!.value,
      fechaPago: this.editForm.get(['fechaPago'])!.value,
      periodoPagado: this.editForm.get(['periodoPagado'])!.value,
      obligacion: this.editForm.get(['obligacion'])!.value,
      cliente: this.editForm.get(['cliente'])!.value,
      banco: this.editForm.get(['banco'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPago>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
