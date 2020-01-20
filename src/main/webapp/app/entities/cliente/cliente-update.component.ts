import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICliente, Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html'
})
export class ClienteUpdateComponent implements OnInit {
  isSaving = false;
  isNit = false;
  editForm = this.fb.group({
    id: [],
    identificacion: [],
    tipoDeDocumento: [],
    nombreDelCliente: [],
    segundoNombreDelCliente: [],
    primerApellido: [],
    segundoApellido: [],
    rezonSocial: [],
    direccion: [],
    telefono: []
  });

  constructor(protected clienteService: ClienteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);
    });
  }

  updateForm(cliente: ICliente): void {
    this.editForm.patchValue({
      id: cliente.id,
      identificacion: cliente.identificacion,
      tipoDeDocumento: cliente.tipoDeDocumento,
      nombreDelCliente: cliente.nombreDelCliente,
      segundoNombreDelCliente: cliente.segundoNombreDelCliente,
      primerApellido: cliente.primerApellido,
      segundoApellido: cliente.segundoApellido,
      rezonSocial: cliente.rezonSocial,
      direccion: cliente.direccion,
      telefono: cliente.telefono
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  private createFromForm(): ICliente {
    return {
      ...new Cliente(),
      id: this.editForm.get(['id'])!.value,
      identificacion: this.editForm.get(['identificacion'])!.value,
      tipoDeDocumento: this.editForm.get(['tipoDeDocumento'])!.value,
      nombreDelCliente: this.editForm.get(['nombreDelCliente'])!.value,
      segundoNombreDelCliente: this.editForm.get(['segundoNombreDelCliente'])!.value,
      primerApellido: this.editForm.get(['primerApellido'])!.value,
      segundoApellido: this.editForm.get(['segundoApellido'])!.value,
      rezonSocial: this.editForm.get(['rezonSocial'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      telefono: this.editForm.get(['telefono'])!.value
    };
  }

  optionChangedChecker(option){
    if(option == "NIT"){
      this.isNit = true;
    } else {
      this.isNit = false;
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>): void {
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
}
