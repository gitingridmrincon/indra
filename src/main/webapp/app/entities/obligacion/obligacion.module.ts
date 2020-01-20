import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ObligacionesSharedModule } from 'app/shared/shared.module';
import { ObligacionComponent } from './obligacion.component';
import { ObligacionDetailComponent } from './obligacion-detail.component';
import { ObligacionUpdateComponent } from './obligacion-update.component';
import { ObligacionDeleteDialogComponent } from './obligacion-delete-dialog.component';
import { obligacionRoute } from './obligacion.route';

@NgModule({
  imports: [ObligacionesSharedModule, RouterModule.forChild(obligacionRoute)],
  declarations: [ObligacionComponent, ObligacionDetailComponent, ObligacionUpdateComponent, ObligacionDeleteDialogComponent],
  entryComponents: [ObligacionDeleteDialogComponent]
})
export class ObligacionesObligacionModule {}
