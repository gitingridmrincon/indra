import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IObligacion } from 'app/shared/model/obligacion.model';
import { ObligacionService } from './obligacion.service';

@Component({
  templateUrl: './obligacion-delete-dialog.component.html'
})
export class ObligacionDeleteDialogComponent {
  obligacion?: IObligacion;

  constructor(
    protected obligacionService: ObligacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.obligacionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('obligacionListModification');
      this.activeModal.close();
    });
  }
}
