import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './home/home.component';
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/compiler/src/core';
import { LoginComponent } from './login/login.component';
import { HttpInterceptorModule } from './service/header-interceptor.service';
import { UsuarioComponent } from './componente/usuario/usuario/usuario.component';
import { UsuarioAddComponent } from './componente/usuario/usuario-add/usuario-add.component';
import { ComponenteslmiComponent } from './componente/componenteslmi/componenteslmi/componenteslmi.component';
import { ComponenteslmiAddComponent } from './componente/componenteslmi/componenteslmi-add/componenteslmi-add.component';

export const appRouters: Routes = [

  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: '', component: LoginComponent },
  { path: 'usuarioList', component: UsuarioComponent },
  { path: 'usuarioAdd', component: UsuarioAddComponent},
  { path: 'usuarioAdd/:id', component: UsuarioAddComponent},
  { path: 'componenteList', component: ComponenteslmiComponent },
  { path: 'componenteAdd', component: ComponenteslmiAddComponent}
];

export const routes: ModuleWithProviders = RouterModule.forRoot(appRouters);

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    UsuarioComponent,
    UsuarioAddComponent,
    ComponenteslmiComponent,
    ComponenteslmiAddComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    routes,
    HttpInterceptorModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
