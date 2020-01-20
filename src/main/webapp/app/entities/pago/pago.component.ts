import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPago } from 'app/shared/model/pago.model';
import { PagoService } from './pago.service';
import { PagoDeleteDialogComponent } from './pago-delete-dialog.component';

@Component({
  selector: 'jhi-pago',
  templateUrl: './pago.component.html'
})
export class PagoComponent implements OnInit, OnDestroy {
  pagos?: IPago[];
  eventSubscriber?: Subscription;

  constructor(protected pagoService: PagoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.pagoService.query().subscribe((res: HttpResponse<IPago[]>) => {
      this.pagos = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPagos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPago): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPagos(): void {
    this.eventSubscriber = this.eventManager.subscribe('pagoListModification', () => this.loadAll());
  }

  delete(pago: IPago): void {
    const modalRef = this.modalService.open(PagoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pago = pago;
  }
}
