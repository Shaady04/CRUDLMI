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
    console.info(this.componente);
  }

}

