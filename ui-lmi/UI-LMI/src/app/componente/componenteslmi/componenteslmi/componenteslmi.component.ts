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

  componentes: Observable<Componentes[]>;
  nome: String;

  constructor(private componentesService: ComponentesService) { }

  ngOnInit() {
    this.componentesService.getComponentesList().subscribe(data => {
      this.componentes = data;
    });
  }

  deleteComponente(id: Number) {
    this.componentesService.deletarComponente(id).subscribe(data => {
      console.log("Retorno do método delete: " + data);
      this.componentesService.getComponentesList().subscribe(data => {
        this.componentes = data;
      });
    });
  }

  consultarComponente() {
    this.componentesService.consultarComponente(this.nome).subscribe(data => {
      this.componentes = data;
    });
  }
}
