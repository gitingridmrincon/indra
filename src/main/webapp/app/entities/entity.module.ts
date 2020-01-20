import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'producto',
        loadChildren: () => import('./producto/producto.module').then(m => m.ObligacionesProductoModule)
      },
      {
        path: 'banco',
        loadChildren: () => import('./banco/banco.module').then(m => m.ObligacionesBancoModule)
      },
      {
        path: 'obligacion',
        loadChildren: () => import('./obligacion/obligacion.module').then(m => m.ObligacionesObligacionModule)
      },
      {
        path: 'pago',
        loadChildren: () => import('./pago/pago.module').then(m => m.ObligacionesPagoModule)
      },
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.ObligacionesClienteModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ObligacionesEntityModule {}
