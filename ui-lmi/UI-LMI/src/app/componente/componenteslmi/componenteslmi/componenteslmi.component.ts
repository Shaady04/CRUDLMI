import { ComponentesService } from '../../../service/componentes.service';
import { Componentes } from '../../../model/componentes';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-componenteslmi',
  templateUrl: './componenteslmi.component.html',
  styleUrls: ['./componenteslmi.component.css']
})
export class ComponenteslmiComponent implements OnInit {

  componentes: Array<Componentes[]>;
  nomeC: String;
  total: Number;

  constructor(private componentesService: ComponentesService) { }

  ngOnInit() {
    this.componentesService.getComponentesList().subscribe(data => {
      this.componentes = data.content;
      this.total = data.totalElements;
    });
  }

  deleteComponente(id: Number, index) {
    this.componentesService.deletarComponente(id).subscribe(data => {
      //console.log("Retorno do método delete: " + data);

      this.componentes.splice(index, 1); /*Remover da tela*/

      //this.componentesService.getComponentesList().subscribe(data => {
      //  this.componentes = data;
      //});

    });
  }

  consultarComponente() {
    this.componentesService.consultarComponente(this.nomeC).subscribe(data => {
      this.componentes = data.content;
      this.total = data.totalElements;
    });
  }

  carregarPagina(pagina) {

    if (this.nomeC !== '') {
      this.componentesService.consultarComponentePorPage(this.nomeC, (pagina - 1)).subscribe(data => {
        this.componentes = data.content;
        this.total = data.totalElements;
      });
    } else {

      this.componentesService.getComponentesListPage(pagina - 1).subscribe(data => {
        this.componentes = data.content;
        this.total = data.totalElements;
      });
    }
  }

  imprimiRelatorioList() {
    return this.componentesService.downloadRelatorioAllList();
  }

  imprimiRelatorioPedidos() {
    return this.componentesService.downloadRelatorioPedidos();
  }

}
