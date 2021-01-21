import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { Table1ComponentsPage, Table1DeleteDialog, Table1UpdatePage } from './table-1.page-object';

const expect = chai.expect;

describe('Table1 e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let table1ComponentsPage: Table1ComponentsPage;
  let table1UpdatePage: Table1UpdatePage;
  let table1DeleteDialog: Table1DeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Table1s', async () => {
    await navBarPage.goToEntity('table-1');
    table1ComponentsPage = new Table1ComponentsPage();
    await browser.wait(ec.visibilityOf(table1ComponentsPage.title), 5000);
    expect(await table1ComponentsPage.getTitle()).to.eq('Table 1 S');
    await browser.wait(ec.or(ec.visibilityOf(table1ComponentsPage.entities), ec.visibilityOf(table1ComponentsPage.noResult)), 1000);
  });

  it('should load create Table1 page', async () => {
    await table1ComponentsPage.clickOnCreateButton();
    table1UpdatePage = new Table1UpdatePage();
    expect(await table1UpdatePage.getPageTitle()).to.eq('Create or edit a Table 1');
    await table1UpdatePage.cancel();
  });

  it('should create and save Table1s', async () => {
    const nbButtonsBeforeCreate = await table1ComponentsPage.countDeleteButtons();

    await table1ComponentsPage.clickOnCreateButton();

    await promise.all([table1UpdatePage.setColumn1Input('Column1')]);

    expect(await table1UpdatePage.getColumn1Input()).to.eq('Column1', 'Expected Column1 value to be equals to Column1');

    await table1UpdatePage.save();
    expect(await table1UpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await table1ComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Table1', async () => {
    const nbButtonsBeforeDelete = await table1ComponentsPage.countDeleteButtons();
    await table1ComponentsPage.clickOnLastDeleteButton();

    table1DeleteDialog = new Table1DeleteDialog();
    expect(await table1DeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Table 1?');
    await table1DeleteDialog.clickOnConfirmButton();

    expect(await table1ComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
