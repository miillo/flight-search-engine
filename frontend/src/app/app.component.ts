import {Component, OnInit} from '@angular/core';
import {Airport, Flight} from './model';
import {FlightService} from './flight.service';
import {tap} from 'rxjs/operators';
import {FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  zoom = 6;

  lat = 51.673858;
  lng = 7.815982;

  private airports: Airport[];
  private journey: Flight[];
  private journeyAirports: Airport[];
  private journeyStart: string;
  private journeyDestination: string;

  constructor(private flightService: FlightService, private fb: FormBuilder) {}

  ngOnInit() {
    this.flightService
      .fetchAirpoirts$()
      .pipe(tap(airports => this.airports = airports))
      .subscribe();
  }

  onSubmit() {
    this.flightService
      .planJourney$(this.journeyStart, this.journeyDestination)
      .pipe(tap(journey => {
        const nodes = [] as Airport[];
        journey.forEach(flight => {
          nodes.push(flight.from);
          nodes.push(flight.to);
        });
        this.journey = journey;
        this.journeyAirports = nodes;
      }))
      .subscribe();
  }
}
