export interface ITable2 {
  id?: number;
  Column2?: string;
  table1_Column1?: ITable2;
}

export class Table2 implements ITable2 {
  constructor(public id?: number, public Column2?: string, public table1_Column1?: ITable2) {}
}
