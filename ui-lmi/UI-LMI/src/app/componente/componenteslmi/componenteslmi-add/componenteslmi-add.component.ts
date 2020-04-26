import { ComponentesService } from './../../../service/componentes.service';
import { Componentes } from './../../../model/componentes';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-componenteslmi-add',
  templateUrl: './componenteslmi-add.component.html',
  styleUrls: ['./componenteslmi-add.component.css']
})
export class ComponenteslmiAddComponent implements OnInit {

  componente = new Componentes();

  constructor(private routeActive: ActivatedRoute, private componentesService: ComponentesService) { }

  ngOnInit() {
    let id = this.routeActive.snapshot.paramMap.get('id');

    if (id != null) {
      this.componentesService.getComponente(id).subscribe(data => {
        this.componente = data;
      });
    }
  }

  salvarComponente() {
    if (this.componente.id != null && this.componente.id.toString().trim() != null) { /*Att ou edit */
      this.componentesService.updateComponente(this.componente).subscribe(data => {
        this.novo();
        console.info("Componente Atualizado:" + data);
        console.log("Base Atualizada" + data);
      });
    } else {
      this.componentesService.salvarComponente(this.componente).subscribe(data => {
        /*Salvando um novo componente */
        this.novo();
        console.info("Base Atualizada:" + data);
        console.log("Base Atualizada [cadastro]" + data);
      });
    }
  }

  novo() {
    this.componente = new Componentes();
  }
}

