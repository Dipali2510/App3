import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITable1 } from 'app/shared/model/table-1.model';
import { Table1Service } from './table-1.service';
import { Table1DeleteDialogComponent } from './table-1-delete-dialog.component';

@Component({
  selector: 'cg-table-1',
  templateUrl: './table-1.component.html',
})
export class Table1Component implements OnInit, OnDestroy {
  table1s?: ITable1[];
  eventSubscriber?: Subscription;

  constructor(protected table1Service: Table1Service, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.table1Service.query().subscribe((res: HttpResponse<ITable1[]>) => (this.table1s = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTable1s();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITable1): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTable1s(): void {
    this.eventSubscriber = this.eventManager.subscribe('table1ListModification', () => this.loadAll());
  }

  delete(table1: ITable1): void {
    const modalRef = this.modalService.open(Table1DeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.table1 = table1;
  }
}
