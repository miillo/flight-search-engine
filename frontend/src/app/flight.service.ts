import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Airport, Flight} from './model';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class FlightService {
  static backendUrl = `http://0.0.0.0:9000`;

  constructor(private http: HttpClient) {}

  planJourney$(start: string, destination: string): Observable<Flight[]> {
    return this.http.post<Flight[]>(`${FlightService.backendUrl}/plan`, {start, destination});
  }

  fetchAirpoirts$(): Observable<Airport[]> {
    return this.http.get<Airport[]>(`${FlightService.backendUrl}/airports`);
  }
}
