import { Component } from '@angular/core';

@Component({
    selector: 'building',

    template: `
        <div *ngIf="building">
        <h2>{{building.label}} details!</h2>
        <div><label>id: </label>{{building.id}}</div>
        <div>
            <label>label: </label>
            <input [(ngModel)]="buiding.label" placeholder="label"/>
        </div>
        </div>
    `
})

export class BuildingComponent {
    id :    number;
    label : string;
}