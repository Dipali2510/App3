import { element, by, ElementFinder } from 'protractor';

export class Table1ComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('cg-table-1 div table .btn-danger'));
  title = element.all(by.css('cg-table-1 div h2#page-heading span')).first();
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

export class Table1UpdatePage {
  pageTitle = element(by.id('cg-table-1-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  Column1Input = element(by.id('field_Column1'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setColumn1Input(Column1: string): Promise<void> {
    await this.Column1Input.sendKeys(Column1);
  }

  async getColumn1Input(): Promise<string> {
    return await this.Column1Input.getAttribute('value');
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

export class Table1DeleteDialog {
  private dialogTitle = element(by.id('cg-delete-table1-heading'));
  private confirmButton = element(by.id('cg-confirm-delete-table1'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
