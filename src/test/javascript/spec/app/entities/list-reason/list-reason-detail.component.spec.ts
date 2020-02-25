import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BlackcvTestModule } from '../../../test.module';
import { ListReasonDetailComponent } from 'app/entities/list-reason/list-reason-detail.component';
import { ListReason } from 'app/shared/model/list-reason.model';

describe('Component Tests', () => {
  describe('ListReason Management Detail Component', () => {
    let comp: ListReasonDetailComponent;
    let fixture: ComponentFixture<ListReasonDetailComponent>;
    const route = ({ data: of({ listReason: new ListReason(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BlackcvTestModule],
        declarations: [ListReasonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ListReasonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ListReasonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load listReason on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.listReason).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
