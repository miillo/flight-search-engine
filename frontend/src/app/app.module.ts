import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AgmCoreModule} from '@agm/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatNativeDateModule} from '@angular/material';
import {MaterialModule} from './material-module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FlightService} from './flight.service';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    MaterialModule,
    MatNativeDateModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    HttpClientModule,

    AgmCoreModule.forRoot({
    })
  ],
  providers: [FlightService],
  bootstrap: [AppComponent]
})
export class AppModule { }
