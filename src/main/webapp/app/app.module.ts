import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { TestProject1SharedModule } from 'app/shared/shared.module';
import { TestProject1CoreModule } from 'app/core/core.module';
import { TestProject1AppRoutingModule } from './app-routing.module';
import { TestProject1HomeModule } from './home/home.module';
import { TestProject1EntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    TestProject1SharedModule,
    TestProject1CoreModule,
    TestProject1HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    TestProject1EntityModule,
    TestProject1AppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class TestProject1AppModule {}
