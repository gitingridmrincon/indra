import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ObligacionesSharedModule } from 'app/shared/shared.module';
import { BancoComponent } from './banco.component';
import { BancoDetailComponent } from './banco-detail.component';
import { BancoUpdateComponent } from './banco-update.component';
import { BancoDeleteDialogComponent } from './banco-delete-dialog.component';
import { bancoRoute } from './banco.route';

@NgModule({
  imports: [ObligacionesSharedModule, RouterModule.forChild(bancoRoute)],
  declarations: [BancoComponent, BancoDetailComponent, BancoUpdateComponent, BancoDeleteDialogComponent],
  entryComponents: [BancoDeleteDialogComponent]
})
export class ObligacionesBancoModule {}
