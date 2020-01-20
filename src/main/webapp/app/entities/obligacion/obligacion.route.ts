import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IObligacion, Obligacion } from 'app/shared/model/obligacion.model';
import { ObligacionService } from './obligacion.service';
import { ObligacionComponent } from './obligacion.component';
import { ObligacionDetailComponent } from './obligacion-detail.component';
import { ObligacionUpdateComponent } from './obligacion-update.component';

@Injectable({ providedIn: 'root' })
export class ObligacionResolve implements Resolve<IObligacion> {
  constructor(private service: ObligacionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IObligacion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((obligacion: HttpResponse<Obligacion>) => {
          if (obligacion.body) {
            return of(obligacion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Obligacion());
  }
}

export const obligacionRoute: Routes = [
  {
    path: '',
    component: ObligacionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'obligacionesApp.obligacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ObligacionDetailComponent,
    resolve: {
      obligacion: ObligacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'obligacionesApp.obligacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ObligacionUpdateComponent,
    resolve: {
      obligacion: ObligacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'obligacionesApp.obligacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ObligacionUpdateComponent,
    resolve: {
      obligacion: ObligacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'obligacionesApp.obligacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
