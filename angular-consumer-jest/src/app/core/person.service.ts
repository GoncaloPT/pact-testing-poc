import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PersonService {
  private BASE_URL = '/person';

  constructor(private httpClient: HttpClient) { }

  list(): Observable<Person>{
    return this.httpClient.get<Person>(`${(this.BASE_URL)}/`);
  }
}


export interface Person{
  id: number;
  name: string;
}
