import { ITable2 } from 'app/shared/model/table-2.model';

export interface ITable1 {
  id?: number;
  Column1?: string;
  table1_Column1?: ITable2;
}

export class Table1 implements ITable1 {
  constructor(public id?: number, public Column1?: string, public table1_Column1?: ITable2) {}
}
