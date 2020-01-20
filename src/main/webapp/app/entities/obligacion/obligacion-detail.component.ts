import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IObligacion } from 'app/shared/model/obligacion.model';

@Component({
  selector: 'jhi-obligacion-detail',
  templateUrl: './obligacion-detail.component.html'
})
export class ObligacionDetailComponent implements OnInit {
  obligacion: IObligacion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ obligacion }) => {
      this.obligacion = obligacion;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
