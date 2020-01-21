export interface Airport {
  id: string;
  lat: number;
  long: number;
  name: string;
}

export interface Flight {
  flightId: number;
  airline: string;
  from: Airport;
  to: Airport;
  distance: number;
}
