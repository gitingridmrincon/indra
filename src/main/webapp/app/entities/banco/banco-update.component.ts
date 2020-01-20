import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBanco, Banco } from 'app/shared/model/banco.model';
import { BancoService } from './banco.service';

@Component({
  selector: 'jhi-banco-update',
  templateUrl: './banco-update.component.html'
})
export class BancoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codigoBanco: [],
    nombre: []
  });

  constructor(protected bancoService: BancoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ banco }) => {
      this.updateForm(banco);
    });
  }

  updateForm(banco: IBanco): void {
    this.editForm.patchValue({
      id: banco.id,
      codigoBanco: banco.codigoBanco,
      nombre: banco.nombre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const banco = this.createFromForm();
    if (banco.id !== undefined) {
      this.subscribeToSaveResponse(this.bancoService.update(banco));
    } else {
      this.subscribeToSaveResponse(this.bancoService.create(banco));
    }
  }

  private createFromForm(): IBanco {
    return {
      ...new Banco(),
      id: this.editForm.get(['id'])!.value,
      codigoBanco: this.editForm.get(['codigoBanco'])!.value,
      nombre: this.editForm.get(['nombre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBanco>>): void {
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
