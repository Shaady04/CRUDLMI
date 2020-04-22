import { AppConstants } from '../app-constants';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ComponentesService {

  constructor(private http: HttpClient) { }

  /*Metodo de carregar*/
  getComponentesList(): Observable<any> {
    return this.http.get<any>(AppConstants.baseUrl2);
  }

  getComponente(id): Observable<any> {
    return this.http.get<any>(AppConstants.baseUrl2 + id);
  }

  deletarComponente(id: Number): Observable<any> {
    return this.http.delete(AppConstants.baseUrl2 + id, { responseType: 'text' });
  }

  consultarComponente(nome: String): Observable<any> {
    return this.http.get(AppConstants.baseUrl2 + "componentesPorNome/" + nome);
  }
}
