import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IObligacion } from 'app/shared/model/obligacion.model';
import { ObligacionService } from './obligacion.service';
import { ObligacionDeleteDialogComponent } from './obligacion-delete-dialog.component';

@Component({
  selector: 'jhi-obligacion',
  templateUrl: './obligacion.component.html'
})
export class ObligacionComponent implements OnInit, OnDestroy {
  obligacions?: IObligacion[];
  eventSubscriber?: Subscription;

  constructor(protected obligacionService: ObligacionService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.obligacionService.query().subscribe((res: HttpResponse<IObligacion[]>) => {
      this.obligacions = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInObligacions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IObligacion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInObligacions(): void {
    this.eventSubscriber = this.eventManager.subscribe('obligacionListModification', () => this.loadAll());
  }

  delete(obligacion: IObligacion): void {
    const modalRef = this.modalService.open(ObligacionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.obligacion = obligacion;
  }
}
