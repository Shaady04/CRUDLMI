import { User } from './../../../model/user';
import { Observable } from 'rxjs';
import { UsuarioService } from './../../../service/usuario.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {

  usuarios: Observable<User[]>;
  nome: String;

  constructor(private usuarioService: UsuarioService) { }

  ngOnInit() {
    this.usuarioService.getUsuarioList().subscribe(data => {
      this.usuarios = data;
    });
  }

  deleteUsuario(id: Number) {
    this.usuarioService.deletarUsuario(id).subscribe(data => {
      console.log("Retorno do método delete: " + data);
      this.usuarioService.getUsuarioList().subscribe(data => {
        this.usuarios = data;
      });
    });
  }

  consultarUser() {
    this.usuarioService.consultarUser(this.nome).subscribe(data => {
      this.usuarios = data;
    });
  }

}
