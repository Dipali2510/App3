import { element, by, ElementFinder } from 'protractor';

export class Table2ComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('cg-table-2 div table .btn-danger'));
  title = element.all(by.css('cg-table-2 div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class Table2UpdatePage {
  pageTitle = element(by.id('cg-table-2-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  Column2Input = element(by.id('field_Column2'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setColumn2Input(Column2: string): Promise<void> {
    await this.Column2Input.sendKeys(Column2);
  }

  async getColumn2Input(): Promise<string> {
    return await this.Column2Input.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class Table2DeleteDialog {
  private dialogTitle = element(by.id('cg-delete-table2-heading'));
  private confirmButton = element(by.id('cg-confirm-delete-table2'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
