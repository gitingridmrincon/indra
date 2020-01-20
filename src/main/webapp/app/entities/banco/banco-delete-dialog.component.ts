import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBanco } from 'app/shared/model/banco.model';
import { BancoService } from './banco.service';

@Component({
  templateUrl: './banco-delete-dialog.component.html'
})
export class BancoDeleteDialogComponent {
  banco?: IBanco;

  constructor(protected bancoService: BancoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bancoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bancoListModification');
      this.activeModal.close();
    });
  }
}
