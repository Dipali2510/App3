import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITable2 } from 'app/shared/model/table-2.model';
import { Table2Service } from './table-2.service';
import { Table2DeleteDialogComponent } from './table-2-delete-dialog.component';

@Component({
  selector: 'cg-table-2',
  templateUrl: './table-2.component.html',
})
export class Table2Component implements OnInit, OnDestroy {
  table2s?: ITable2[];
  eventSubscriber?: Subscription;

  constructor(protected table2Service: Table2Service, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.table2Service.query().subscribe((res: HttpResponse<ITable2[]>) => (this.table2s = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTable2s();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITable2): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTable2s(): void {
    this.eventSubscriber = this.eventManager.subscribe('table2ListModification', () => this.loadAll());
  }

  delete(table2: ITable2): void {
    const modalRef = this.modalService.open(Table2DeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.table2 = table2;
  }
}
