import { Componentes } from './../model/componentes';
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

  getComponentesListPage(pagina): Observable<any> {
    return this.http.get<any>(AppConstants.baseUrl2 + 'page/' + pagina);
  }

  getComponente(id): Observable<any> {
    return this.http.get<any>(AppConstants.baseUrl2 + id);
  }

  deletarComponente(id: Number): Observable<any> {
    var x = confirm("Tem certeza?");
    if (x == true) {
      return this.http.delete(AppConstants.baseUrl2 + id, { responseType: 'text' });
    }
  }

  consultarComponente(nome: String): Observable<any> {
    return this.http.get(AppConstants.baseUrl2 + "componentesPorNome/" + nome);
  }

  consultarComponentePorPage(nome: String, page: Number): Observable<any> {
    return this.http.get(AppConstants.baseUrl2 + "componentesPorNome/" + nome + "/page/" + page);
  }

  salvarComponente(componentes): Observable<any> {
    alert("Componente Cadastrado!");
    return this.http.post(AppConstants.baseUrl2, componentes);
  }

  updateComponente(componentes): Observable<any> {
    alert("Componente Atualizado!");
    return this.http.put(AppConstants.baseUrl2, componentes);
  }

}
