import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBanco } from 'app/shared/model/banco.model';
import { BancoService } from './banco.service';
import { BancoDeleteDialogComponent } from './banco-delete-dialog.component';

@Component({
  selector: 'jhi-banco',
  templateUrl: './banco.component.html'
})
export class BancoComponent implements OnInit, OnDestroy {
  bancos?: IBanco[];
  eventSubscriber?: Subscription;

  constructor(protected bancoService: BancoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.bancoService.query().subscribe((res: HttpResponse<IBanco[]>) => {
      this.bancos = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBancos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBanco): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBancos(): void {
    this.eventSubscriber = this.eventManager.subscribe('bancoListModification', () => this.loadAll());
  }

  delete(banco: IBanco): void {
    const modalRef = this.modalService.open(BancoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.banco = banco;
  }
}
