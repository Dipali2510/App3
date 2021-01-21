import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { Table2ComponentsPage, Table2DeleteDialog, Table2UpdatePage } from './table-2.page-object';

const expect = chai.expect;

describe('Table2 e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let table2ComponentsPage: Table2ComponentsPage;
  let table2UpdatePage: Table2UpdatePage;
  let table2DeleteDialog: Table2DeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Table2s', async () => {
    await navBarPage.goToEntity('table-2');
    table2ComponentsPage = new Table2ComponentsPage();
    await browser.wait(ec.visibilityOf(table2ComponentsPage.title), 5000);
    expect(await table2ComponentsPage.getTitle()).to.eq('Table 2 S');
    await browser.wait(ec.or(ec.visibilityOf(table2ComponentsPage.entities), ec.visibilityOf(table2ComponentsPage.noResult)), 1000);
  });

  it('should load create Table2 page', async () => {
    await table2ComponentsPage.clickOnCreateButton();
    table2UpdatePage = new Table2UpdatePage();
    expect(await table2UpdatePage.getPageTitle()).to.eq('Create or edit a Table 2');
    await table2UpdatePage.cancel();
  });

  it('should create and save Table2s', async () => {
    const nbButtonsBeforeCreate = await table2ComponentsPage.countDeleteButtons();

    await table2ComponentsPage.clickOnCreateButton();

    await promise.all([table2UpdatePage.setColumn2Input('Column2')]);

    expect(await table2UpdatePage.getColumn2Input()).to.eq('Column2', 'Expected Column2 value to be equals to Column2');

    await table2UpdatePage.save();
    expect(await table2UpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await table2ComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Table2', async () => {
    const nbButtonsBeforeDelete = await table2ComponentsPage.countDeleteButtons();
    await table2ComponentsPage.clickOnLastDeleteButton();

    table2DeleteDialog = new Table2DeleteDialog();
    expect(await table2DeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Table 2?');
    await table2DeleteDialog.clickOnConfirmButton();

    expect(await table2ComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
