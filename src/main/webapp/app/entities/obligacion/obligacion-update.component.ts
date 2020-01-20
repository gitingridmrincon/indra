import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { IObligacion, Obligacion } from 'app/shared/model/obligacion.model';
import { ObligacionService } from './obligacion.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';

@Component({
  selector: 'jhi-obligacion-update',
  templateUrl: './obligacion-update.component.html'
})
export class ObligacionUpdateComponent implements OnInit {
  isSaving = false;

  productos: IProducto[] = [];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    codigoObligacion: [],
    nombre: [],
    descripcion: [],
    fecha: [],
    valorTotal: [],
    valorPeriodo: [],
    nomeroPeriodos: [],
    periodoActual: [],
    vigente: [],
    estado: [],
    producto: []
  });

  constructor(
    protected obligacionService: ObligacionService,
    protected productoService: ProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ obligacion }) => {
      this.updateForm(obligacion);

      this.productoService
        .query()
        .pipe(
          map((res: HttpResponse<IProducto[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProducto[]) => (this.productos = resBody));
    });
  }

  updateForm(obligacion: IObligacion): void {
    this.editForm.patchValue({
      id: obligacion.id,
      codigoObligacion: obligacion.codigoObligacion,
      nombre: obligacion.nombre,
      descripcion: obligacion.descripcion,
      fecha: obligacion.fecha,
      valorTotal: obligacion.valorTotal,
      valorPeriodo: obligacion.valorPeriodo,
      nomeroPeriodos: obligacion.nomeroPeriodos,
      periodoActual: obligacion.periodoActual,
      vigente: obligacion.vigente,
      estado: obligacion.estado,
      producto: obligacion.producto
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const obligacion = this.createFromForm();
    if (obligacion.id !== undefined) {
      this.subscribeToSaveResponse(this.obligacionService.update(obligacion));
    } else {
      this.subscribeToSaveResponse(this.obligacionService.create(obligacion));
    }
  }

  private createFromForm(): IObligacion {
    return {
      ...new Obligacion(),
      id: this.editForm.get(['id'])!.value,
      codigoObligacion: this.editForm.get(['codigoObligacion'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      fecha: this.editForm.get(['fecha'])!.value,
      valorTotal: this.editForm.get(['valorTotal'])!.value,
      valorPeriodo: this.editForm.get(['valorPeriodo'])!.value,
      nomeroPeriodos: this.editForm.get(['nomeroPeriodos'])!.value,
      periodoActual: this.editForm.get(['periodoActual'])!.value,
      vigente: this.editForm.get(['vigente'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      producto: this.editForm.get(['producto'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObligacion>>): void {
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

  trackById(index: number, item: IProducto): any {
    return item.id;
  }
}
